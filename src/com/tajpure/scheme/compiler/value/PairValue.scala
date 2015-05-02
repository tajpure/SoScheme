package com.tajpure.scheme.compiler.value

class PairValue(_head: Value, _tail: Value) extends Value {

  val head = _head
  
  val tail = _tail
  
  override
  def toString(): String = {
    "(" + head + " . " + tail + ")"
  }
}