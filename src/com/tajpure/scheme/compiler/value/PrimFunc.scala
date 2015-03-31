package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Node

abstract class PrimFunc(_name: String, _arity: Int) extends Value {
  
  val name: String = _name
  
  val arity: Int = _arity

  def apply(args: List[Value], location: Node): Value;
  
  def typecheck(args: List[Value], location: Node): Value;
  
  override
  def toString(): String = {
    name
  }
  
}