package com.tajpure.scheme.compiler.value

class FractionType (_numerator: Int, _denominator: Int) extends Value {
  
  val numerator: Int = _numerator
  
  val denominator: Int = _denominator
  
  override
  def toString(): String = {
    numerator.toString + "/" + denominator.toString
  }
  
}