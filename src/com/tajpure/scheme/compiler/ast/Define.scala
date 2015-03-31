package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Define(_pattern: Node, _value: Node, _file: String, _start: Int, _end: Int,
             _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  val pattern: Node = _pattern
  
  val value: Node = _value

  def interp(s: Scope): Value = {
    val vValue: Value = value.interp(s)
    s.define(pattern, vValue)
    Value.VOID
  }

  def typeCheck(s: Scope): Value = {
    val vValue: Value = value.typeCheck(s)
    s.define(pattern, vValue)
    Value.VOID
  }
  
  def codegen(s: Scope): Value = {
    null
  }
  
  override
  def toString(): String = {
    "define " + pattern.toString() + " " + value.toString()
  }

}