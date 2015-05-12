package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class BoolValue(val value: Boolean) extends Value {

  override
  def toString: String = {
    if (value) {
      "#t"
    }
    else {
      "#f"
    }
  }
  
}