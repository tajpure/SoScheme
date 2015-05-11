package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.exception.RunTimeException

class IntValue(val value: Long) extends Value {
  
  override
  def +(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new IntValue(value + that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
      new FloatValue(value + that.asInstanceOf[FloatValue].value)
    }
    else if (that.isInstanceOf[CharValue]) {
      new CharValue(value + that.asInstanceOf[CharValue].value)
    }
    else if (that.isInstanceOf[StringValue]) {
      new StringValue(value + that.asInstanceOf[StringValue].value)
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def -(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new IntValue(value - that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
      new FloatValue(value - that.asInstanceOf[FloatValue].value)
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def *(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new IntValue(value * that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
      new FloatValue(value * that.asInstanceOf[FloatValue].value)
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def /(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new IntValue(value / that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
      new FloatValue(value / that.asInstanceOf[FloatValue].value)
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def >(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new BoolValue(value > that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
     new BoolValue(value > that.asInstanceOf[FloatValue].value)
    }
    else if (that.isInstanceOf[CharValue]) {
      new BoolValue(value > that.asInstanceOf[CharValue].value.charAt(0))
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def <(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new BoolValue(value < that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
     new BoolValue(value < that.asInstanceOf[FloatValue].value)
    }
    else if (that.isInstanceOf[CharValue]) {
      new BoolValue(value < that.asInstanceOf[CharValue].value.charAt(0))
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def <=(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new BoolValue(value <= that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
     new BoolValue(value <= that.asInstanceOf[FloatValue].value)
    }
    else if (that.isInstanceOf[CharValue]) {
      new BoolValue(value <= that.asInstanceOf[CharValue].value.charAt(0))
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def >=(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new BoolValue(value >= that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
     new BoolValue(value >= that.asInstanceOf[FloatValue].value)
    }
    else if (that.isInstanceOf[CharValue]) {
      new BoolValue(value >= that.asInstanceOf[CharValue].value.charAt(0))
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def ==(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new BoolValue(value == that.asInstanceOf[IntValue].value)
    }
    else if (that.isInstanceOf[FloatValue]) {
     new BoolValue(value == that.asInstanceOf[FloatValue].value)
    }
    else if (that.isInstanceOf[CharValue]) {
      new BoolValue(value == that.asInstanceOf[CharValue].value.charAt(0))
    }
    else {
      throw new RunTimeException("type error")
    }
  }
  
  override
  def toString(): String = {
    value.toString()
  }
  
}

object IntValue extends App {
//  val intVal = new IntValue(10)
}