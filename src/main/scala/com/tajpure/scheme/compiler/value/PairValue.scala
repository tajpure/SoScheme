package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

class PairValue(_head: Value, _tail: Value) extends Value {

  val head = _head
  
  val tail = _tail
  
  override
  def toString(): String = {
    def rest(tail: Value): String = {
      if (tail.isInstanceOf[PairValue]) {
        val pair = tail.asInstanceOf[PairValue]
        " " + pair.head + rest(pair.tail)
      }
      else if (tail.isInstanceOf[VoidList]) {
        ""
      }
      else {
        " . " + tail
      }
    }
    "(" + head + rest(tail) + ")"
  }
}