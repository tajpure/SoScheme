package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class StringValue(_value: String) extends Value {

  val value: String = _value

  def apply(args: List[Value], location: Node): Value = {
    null
  }
  
  override
  def toString(): String = {
    value
  }
}