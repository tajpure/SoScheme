package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException

class Define(_pattern: Node, _value: Node, _file: String, _start: Int, _end: Int,
             _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  def this(_pattern: Node, _value: Node, node: Node) = 
    this(_pattern, _value, node.file, node.start, node.end, node.row, node.col)
  
  val pattern: Node = _pattern
  
  val value: Node = _value

  def interp(s: Scope): Value = {
    val vValue: Value = value.interp(s)
    s.define(pattern, vValue)
    Value.VOID
  }

  def typecheck(s: Scope): Value = {
    val vValue: Value = value.typecheck(s)
    s.define(pattern, vValue)
    Value.VOID
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    if (value.isInstanceOf[Func]) {
      val closure = value.interp(s)
      s.define(_pattern, closure)
      value.codegen(pattern, s)
    }
    else {
      val _value = value.codegen(s)
      val pointer = s.codegen.builder.buildAlloca(_value.typeOf(), pattern.toString())
      s.putValue0(pattern.toString(), pointer)
      s.codegen.builder.buildStore(_value, pointer)
    }
  }
  
  override
  def toString(): String = {
    "define " + pattern.toString() + " " + value.toString()
  }

}