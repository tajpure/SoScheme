package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.Closure
import com.tajpure.scheme.compiler.exception.CompilerException

import org.jllvm._type.FunctionType
import org.jllvm.bindings.LLVMLinkage
import org.jllvm._type.IntegerType
import org.jllvm._type.Type
import org.jllvm.value.BasicBlock
import org.jllvm._type.PointerType
import org.jllvm.value.user.constant.Function
import org.jllvm.value.user.instruction.LoadInstruction
import org.jllvm.value.user.instruction.StoreInstruction

class Func(val params: Node, val propertyForm: Scope, val body: Node, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  def this(_params: Node, _propertyForm: Scope, _body: Node, node: Node) = 
    this(_params, _propertyForm, _body, node.file, node.start, node.end, node.row, node.col)
  
  def interp(s: Scope): Value = {
    val properties: Scope = 
      if (propertyForm == null) {
      null
    } else {
      Scope.evalProperties(propertyForm, s)
    }
    new Closure(this, properties, s)
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    val paramList = if (params.isInstanceOf[Tuple]) {
          params.asInstanceOf[Tuple].elements.map { node => node.asInstanceOf[Name] }
        } else if (params.isInstanceOf[Name]) {
          List(params.asInstanceOf[Name])
        } else {
          throw new CompilerException("incorrent argument", this)
        }
    val _params: Array[Type] = paramList.map { param => s.codegen.any }.toArray
    val function: Function = new Function(s.codegen.module, "anonymous", new FunctionType(s.codegen.any, _params, false))
    
    function.setLinkage(LLVMLinkage.LLVMExternalLinkage)
    paramList.zipWithIndex.foreach { case (param, i) => s.putValueLLVM(param.id, function.getParameter(i))} 
    s.putValueLLVM("this", function)
    
    val block: BasicBlock = function.appendBasicBlock("entry")
    s.codegen.builder.positionBuilderAtEnd(block)
    val last = body.codegen(s)
    s.codegen.builder.buildRet(last)
    function
  }
  
  override
  def codegen(node: Node, s: Scope): org.jllvm.value.Value = {
    val paramList = if (params.isInstanceOf[Tuple]) {
          params.asInstanceOf[Tuple].elements.map { node => node.asInstanceOf[Name] }
        } else if (params.isInstanceOf[Name]) {
          List(params.asInstanceOf[Name])
        } else {
          throw new CompilerException("incorrent argument", this)
        }
    val _params: Array[Type] = paramList.map { param => s.codegen.any }.toArray
    val function: Function = new Function(s.codegen.module, node.toString(), new FunctionType(s.codegen.any, _params, false))
      
    function.setLinkage(LLVMLinkage.LLVMExternalLinkage)
    s.putValueLLVM(node.toString(), function)
    paramList.zipWithIndex.foreach { case (param, i) => s.putValueLLVM(param.id, function.getParameter(i))} 
    s.putValueLLVM("this", function)
      
    val block: BasicBlock = function.appendBasicBlock("entry")
    s.codegen.builder.positionBuilderAtEnd(block)
    val last = body.codegen(s)
    val ret =
      if (last.isInstanceOf[Function] || last.isInstanceOf[LoadInstruction] || last.isInstanceOf[StoreInstruction]) {
       val void = s.codegen.builder.buildAlloca(s.codegen.any, "ret")
       s.codegen.builder.buildLoad(void, "void")
      } else {
        last
      }
    s.codegen.builder.buildRet(ret)
    function
  }
  
  override
  def toString: String = {
    "function args:" + params + " body:" + body
  }
  
}