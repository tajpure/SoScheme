package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class FloatValue(_vlaue: Float) extends Value {
  
  val value: Float = _vlaue
  
  def +(that: IntValue): FloatValue = {
    new FloatValue(value + that.value)
  }
  
  def +(that: FloatValue): FloatValue = {
    new FloatValue(value + that.value)
  }
  
  override
  def toString(): String = {
    value.toString()
  }
  
}