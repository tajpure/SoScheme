package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

class FractionValue (_numerator: Long, _denominator: Long) extends Value {
  
  // greatest common divisor
  val gcd: Long = gcd(_numerator, _denominator) 
  
  val numerator: Long = _numerator / gcd
  
  val denominator: Long = _denominator / gcd

  def apply(args: List[Value], location: Node): Value = {
    null
  }
  
  def gcd(numerator: Long, denominator: Long): Long = {
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