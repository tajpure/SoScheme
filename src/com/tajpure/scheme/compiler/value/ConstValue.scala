package com.tajpure.scheme.compiler.value

class ConstValue(_value: Object) extends Value {

  val value: Object = _value
  
  override
  def toString(): String = {
    value.toString()
  }
  
}