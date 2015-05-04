package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node

class IsPort extends PrimFunc("port?", 1) {
  
  def apply(args: List[Value], location: Node): Value = {
    // TODO
    null
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    // TODO
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    // TODO
    null
  }

  override
  def toString: String = {
    "port?"
  }
  
}