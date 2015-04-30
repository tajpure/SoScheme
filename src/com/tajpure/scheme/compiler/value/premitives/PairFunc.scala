package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.ast.Node

class PairFunc extends PrimFunc("pair" , 2) {

  def apply(args: List[Value], location: Node): Value = {
    args.foreach { arg => println(arg) }
    Value.VOID
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    null
  }

  override
  def toString: String = {
    "pair"
  }
}