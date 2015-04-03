package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class StringType extends Value {

  def apply(args: List[Value], location: Node): Value = {
    null
  }

  override
  def toString: String = {
    "String"
  }
  
}