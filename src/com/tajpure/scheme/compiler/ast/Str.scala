package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.StringValue

class Str(_value: String, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {

  val value = _value

  def interp(s: Scope): Value = {
    new StringValue(value)
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): Value = {
    null
  }
  
  override
  def toString(): String = {
    "\"" + value + "\""
  }
  
}