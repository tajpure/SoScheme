package com.tajpure.scheme.compiler.value

class CharValue(val value: String) extends Value {
  
  override
  def toString(): String = {
    value
  }
  
}