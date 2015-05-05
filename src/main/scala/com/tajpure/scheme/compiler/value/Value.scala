package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.VoidValue;

import com.tajpure.scheme.compiler.ast.Node

abstract class Value {

}

object Value {
  
  val VOID: Value = new VoidValue();
  
}