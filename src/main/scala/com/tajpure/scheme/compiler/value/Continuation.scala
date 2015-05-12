package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class Continuation(val value: Value) extends Value {
  
  override
  def toString(): String = {
    value.toString()
  }
}