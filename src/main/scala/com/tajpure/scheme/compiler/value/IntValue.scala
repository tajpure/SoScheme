package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class IntValue(_value: Long) extends Value {
  
  val value: Long = _value
  
  def +(that: IntValue): IntValue = {
    new IntValue(value + that.value)
  }
  
  def +(that: FloatValue): FloatValue = {
    new FloatValue(value + that.value)
  }
  
  def +(that: CharValue): IntValue = {
    new IntValue(value + that.value.toInt)
  }
  
  def +(that: StringValue): StringValue = {
    new StringValue(value + that.value)
  }
  
  override
  def toString(): String = {
    value.toString()
  }
  
}

object IntValue extends App {
//  val intVal = new IntValue(10)
}