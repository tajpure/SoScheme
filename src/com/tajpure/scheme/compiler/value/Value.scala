package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

abstract class Value {

}

object Value {
  
  val VOID: Value = new VoidValue();
  
}