package com.tajpure.scheme.compiler.value

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