package com.tajpure.scheme.compiler.value

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