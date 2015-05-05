package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.util.Log

class SetValue extends PrimFunc("set!" , 2) {
  
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
    "set!"
  }
  
}