package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

class CharValue(_value: String) extends Value {
  
  val value: String = _value
  
  override
  def toString(): String = {
    value
  }
  
}