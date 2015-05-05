package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

import com.tajpure.scheme.compiler.ast.Node

class FloatValue(_vlaue: Float) extends Value {
  
  val value: Float = _vlaue
  
  override
  def toString(): String = {
    value.toString()
  }
  
}