package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.exception.CompilerException

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
        throw new CompilerException("", this)
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
        null
      }
    }
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    null
  }
  
}