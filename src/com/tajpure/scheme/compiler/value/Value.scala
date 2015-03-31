package com.tajpure.scheme.compiler.value

abstract class Value {

}

object Value {
  
  val VOID: Value = new VoidValue();
  
}