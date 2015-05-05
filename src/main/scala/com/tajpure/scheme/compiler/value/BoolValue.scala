package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

import com.tajpure.scheme.compiler.ast.Node

class BoolValue(_value: Boolean) extends Value {

  val value: Boolean = _value
  
  override
  def toString: String = {
    if (value) {
      "#t"
    }
    else {
      "#f"
    }
  }
  
}