package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class IntValue(_value: Long) extends Value {
  
  val value: Long = _value
  
  override
  def toString(): String = {
    value.toString()
  }
  
}

object IntValue extends App {
//  val intVal = new IntValue(10)
}