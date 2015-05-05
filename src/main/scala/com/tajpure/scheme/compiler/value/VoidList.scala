package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

class VoidList extends Value {
  
  override
  def toString(): String = {
    "()"
  }
}