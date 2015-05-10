package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

abstract class Value {

  def +(that: Value): Value = {
    Value.VOID
  }
  
  def -(that: Value): Value = {
    Value.VOID
  }
  
  def *(that: Value): Value = {
    Value.VOID
  }
  
  def /(that: Value): Value = {
    Value.VOID
  }
  
}

object Value {
  
  val VOID: Value = new VoidValue();
  
}