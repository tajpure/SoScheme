package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Closure
import org.jllvm._type.FunctionType
import org.jllvm.bindings.LLVMLinkage
import org.jllvm._type.IntegerType
import org.jllvm._type.Type
import org.jllvm.value.BasicBlock

class Func(_params: List[Symbol], _propertyForm: Scope, _body: Node, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  val params: List[Symbol] = _params
  
  val propertyForm: Scope = _propertyForm
  
  val body: Node = _body
  
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
    val _type: IntegerType = new IntegerType(32)
    val Doubles:  Array[Type] = new Array[Type](0)
    val function: org.jllvm.value.user.constant.Function = 
      new org.jllvm.value.user.constant.Function(s.codegen.module, "test", new FunctionType(_type, Doubles, false))
    function.setLinkage(LLVMLinkage.LLVMExternalLinkage)
    val block: BasicBlock = function.appendBasicBlock("entry");
    s.codegen.builder.positionBuilderAtEnd(block)
    function
  }
  
  override
  def toString: String = {
    "function: " + params + " " + body
  }
  
}