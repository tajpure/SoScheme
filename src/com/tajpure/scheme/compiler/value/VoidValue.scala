package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class VoidValue extends Value {

  def apply(args: List[Value], location: Node): Value = {
    null
  }
  
  override
  def toString(): String = {
    "Void"
  }

}