package com.tajpure.scheme.compiler.ast

import org.jllvm.value.user.constant.ConstantBoolean
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.value.Value
import org.jllvm.value.BasicBlock
import org.jllvm.value.user.constant.Function
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.RunTimeException
import org.jllvm._type.VoidType
import org.jllvm.value.user.constant.ConstantInteger

class If(_test: Node, _then: Node, _else: Node, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  def this(_test: Node, _then: Node, _else: Node, node: Node) = 
    this(_test, _then, _else, node.file, node.start, node.end, node.row, node.col)
  
  val test = _test
  
  val then = _then
  
  val else_ = _else
  
  def interp(s: Scope): Value = {
    val result = test.interp(s)
    val thenOrElse = if (!result.isInstanceOf[BoolValue]) {
        throw new CompilerException("error type", this)
      } else {
        result.asInstanceOf[BoolValue].value
      }
    if (thenOrElse) {
      then.interp(s)
    }
    else {
      if (else_ != null) {
        else_.interp(s)
      }
      else {
        Value.VOID
      }
    }
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    val thisFunc = s.lookup0("this")
    val function: Function = if (thisFunc.isInstanceOf[Function]) {
        thisFunc.asInstanceOf[Function]
      } else {
        throw new RunTimeException("redefined \"this\"", this)
      }
    val thenBlock: BasicBlock = function.appendBasicBlock("then")
    val elseBlock: BasicBlock = function.appendBasicBlock("else")
    val endBlock: BasicBlock = function.appendBasicBlock("end")
    val testValue = test.codegen(s)
    
    s.codegen.builder.buildCondBr(testValue, thenBlock, elseBlock)
    s.codegen.builder.positionBuilderAtEnd(thenBlock)
    val thenValue = then.codegen(s)
    s.codegen.builder.buildBr(endBlock)
    
    s.codegen.builder.positionBuilderAtEnd(elseBlock)
    val elseValue = if (else_ != null) {
        val elseValueTmp = else_.codegen(s)
        s.codegen.builder.buildBr(endBlock)
        elseValueTmp
      } else {
        s.codegen.builder.buildBr(endBlock)
        ConstantInteger.constI32(0) // when else is null, the value of else will be 0
      }
    
    s.codegen.builder.positionBuilderAtEnd(endBlock)
    
    val result = s.codegen.builder.buildPhi(thenValue.typeOf(), "result")
    result.addIncoming(Array(thenValue, elseValue), Array(thenBlock, elseBlock))
    result
    
  }
  
}