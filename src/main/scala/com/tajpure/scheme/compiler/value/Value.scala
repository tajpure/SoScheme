package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.exception.RunTimeException

abstract class Value {
  
  val error = "abstract value method can't be executed"
  
  def +(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def -(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def *(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def /(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def >(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def <(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def <=(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def >=(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
  def ==(that: Value): Value = {
    throw new RunTimeException(error)
  }
  
}

object Value {
  
  val VOID: Value = new VoidValue();
  
}