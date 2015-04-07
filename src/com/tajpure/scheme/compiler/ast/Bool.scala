package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.value.Type

class Bool(_content: String, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  val content: String = _content
  
  val value: Boolean = content match {
    case "#t" => true
    case "#f" => false
  }

  def interp(s: Scope): Value = {
    new BoolValue(value)
  }

  def typecheck(s: Scope): Value = {
    Type.BOOL
  }
  
  def codegen(s: Scope): Value = {
    null
  }
  
  override
  def toString(): String = {
    content
  }
  
}