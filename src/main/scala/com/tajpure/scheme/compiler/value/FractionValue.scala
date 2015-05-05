package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class FractionValue (_numerator: Int, _denominator: Int) extends Value {
  
  // greatest common divisor
  val gcd: Int = gcd(_numerator, _denominator) 
  
  val numerator: Int = _numerator / gcd
  
  val denominator: Int = _denominator / gcd

  def apply(args: List[Value], location: Node): Value = {
    null
  }
  
  def gcd(numerator: Int, denominator: Int): Int = {
    if (denominator == 0) {
      numerator
    } else {
      gcd(denominator, numerator % denominator)
    }
  }
  
  override
  def toString(): String = {
    denominator match {
      case 1 => numerator.toString
      case _ => numerator.toString + "/" + denominator.toString
    }
  }
  
}

object FractionValue extends App {
  val fraction = new FractionValue(12, 4)
  println(fraction.gcd(12, 4))
}