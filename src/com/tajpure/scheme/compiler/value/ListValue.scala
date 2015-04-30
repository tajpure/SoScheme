package com.tajpure.scheme.compiler.value

class ListValue(_list: List[Value]) extends Value {
  
  val value = _list
  
  def this() = this(List[Value]())
  
  def add(_list: ListValue): ListValue = {
    new ListValue(value.:::(_list.value))
  }
  
  override
  def toString(): String = {
    value.toString()
  }
  
}