package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class FractionType (_numerator: Int, _denominator: Int) extends Value {
  
  val numerator: Int = _numerator
  
  val denominator: Int = _denominator

  def apply(args: List[Value], location: Node): Value = {
    null
  }
  
  override
  def toString(): String = {
    numerator.toString + "/" + denominator.toString
  }
  
}