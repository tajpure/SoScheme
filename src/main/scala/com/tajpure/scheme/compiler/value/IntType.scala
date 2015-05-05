package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.Value;

import com.tajpure.scheme.compiler.ast.Node

class IntType extends Value {

  override
  def toString: String = {
    "Int"
  }
  
}