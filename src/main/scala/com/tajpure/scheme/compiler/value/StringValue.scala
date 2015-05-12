package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.exception.RunTimeException

class StringValue(val value: String) extends Value {

  override def +(that: Value): Value = {
    if (that.isInstanceOf[IntValue]) {
      new StringValue(value + that.asInstanceOf[IntValue].value)
    } 
    else if (that.isInstanceOf[FloatValue]) {
      new StringValue(value + that.asInstanceOf[FloatValue].value)
    } 
    else if (that.isInstanceOf[CharValue]) {
      new StringValue(value + that.asInstanceOf[CharValue].value)
    } 
    else if (that.isInstanceOf[StringValue]) {
      new StringValue(value + that.asInstanceOf[StringValue].value)
    } 
    else {
      throw new RunTimeException("type error")
    }
  }

  override def toString(): String = {
    value
  }
}