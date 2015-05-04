package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.exception.CompilerException

class IsNumber extends PrimFunc("number?", 1) {
  
  def apply(args: List[Value], location: Node): Value = {
    if (args.size != arity) {
      throw new CompilerException("args don't match the 'number?' function", location)
    }
    else {
      if (args(0).isInstanceOf[IntValue] || args(0).isInstanceOf[FloatValue]) {
        new BoolValue(true)
      }
      else {
        new BoolValue(false)
      }
    }
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
    "number?"
  }
  
}