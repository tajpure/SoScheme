package com.tajpure.scheme.compiler.value

import main.scala.com.tajpure.scheme.compiler.value.BoolType;
import main.scala.com.tajpure.scheme.compiler.value.FloatType;
import main.scala.com.tajpure.scheme.compiler.value.IntType;
import main.scala.com.tajpure.scheme.compiler.value.StringType;
import main.scala.com.tajpure.scheme.compiler.value.Type;

class Type {
  
  val BOOL: Value = Type.BOOL
  
  val INT: Value = Type.INT
  
  val FLOAT: Value = Type.FLOAT
  
  val STRING: Value = Type.STRING
  
}

object Type {
  
  val BOOL: Value = new BoolType()
  
  val INT: Value = new IntType()
  
  val FLOAT: Value = new FloatType()
  
  val STRING: Value = new StringType()
  
}