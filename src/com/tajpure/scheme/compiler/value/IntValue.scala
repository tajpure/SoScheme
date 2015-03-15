package com.tajpure.scheme.compiler.value

class IntValue(_value: Int) extends Value {
  
  def value: Int = _value
  
  override
  def toString(): String = {
    value.toString()
  }
  
}