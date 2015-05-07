package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class Continuation(_value: Value) extends Value {
  
  val value: Value = _value
  
  override
  def toString(): String = {
    value.toString()
  }
}