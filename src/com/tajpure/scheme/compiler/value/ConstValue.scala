package com.tajpure.scheme.compiler.value

class ConstValue(_value: String) extends Value {

  val value: String = _value
  
  override
  def toString(): String = {
    value
  }
  
}