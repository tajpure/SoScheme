package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.IntValue

class Or extends PrimFunc("or" , 2) {
  
  def apply(args: List[Value], location: Node): Value = {
    if (args.size < arity) {
      throw new CompilerException("args don't match the 'or' function", location)
    }
    if (args(0).isInstanceOf[BoolValue] && args(1).isInstanceOf[BoolValue]) {
      new BoolValue(args(0).asInstanceOf[BoolValue].value || args(1).asInstanceOf[BoolValue].value)
    }
    else {
      Log.error(location, "args type error in function 'or'")
      Value.VOID
    }
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    if (args.size != 1) {
      throw new CompilerException("incorrect arguments count in call 'or'", location)
    }
    else if (args(0).isInstanceOf[org.jllvm.value.Value] && args(1).isInstanceOf[org.jllvm.value.Value]) {
      s.codegen.builder.buildOr(args(0), args(1), "or")
    }
    else {
      throw new CompilerException("incorrect arguments", location)
    }
  }

  override
  def toString: String = {
    "or"
  }
  
}