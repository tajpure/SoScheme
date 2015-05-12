package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.value.premitives.ListFunc
import com.tajpure.scheme.compiler.ast.Node

class PairValue(val head: Value, val tail: Value) extends Value {

  def toList(): List[Value] = {
    def rest(tail: Value): List[Value] = {
       if (tail.isInstanceOf[PairValue]) {
        val pair = tail.asInstanceOf[PairValue]
         List(pair.head).++(rest(pair.tail))
      }
      else {
        List(tail)
      }
    }
    val list = rest(tail).+:(head)
    list.filter { value => !value.isInstanceOf[VoidList] }
  }
  
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