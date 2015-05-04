package com.tajpure.scheme.compiler.value

class CharValue(_value: String) extends Value {
  
  val value: String = _value
  
  override
  def toString(): String = {
    value
  }
  
}