package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

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