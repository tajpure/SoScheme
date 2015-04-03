package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class FloatValue(_vlaue: Float) extends Value {
  
  val value: Float = _vlaue
  
  override
  def toString(): String = {
    value.toString()
  }
  
}