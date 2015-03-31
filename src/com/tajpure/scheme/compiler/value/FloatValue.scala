package com.tajpure.scheme.compiler.value

class FloatValue(_vlaue: Float) extends Value {
  
  val value: Float = _vlaue
  
  override
  def toString(): String = {
    value.toString()
  }
  
}