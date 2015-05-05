package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

import com.tajpure.scheme.compiler.ast.Node

class IntValue(_value: Int) extends Value {
  
  val value: Int = _value
  
  override
  def toString(): String = {
    value.toString()
  }
  
}

object IntValue extends App {
//  val intVal = new IntValue(10)
  println("ssssss")
}