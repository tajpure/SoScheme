package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.IntValue

class Not extends PrimFunc("not", 1) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size != arity) {
      throw new CompilerException("args don't match the 'not' function", location)
    }

    if (args(0).isInstanceOf[BoolValue]) {
      new BoolValue(!args(0).asInstanceOf[BoolValue].value)
    }
    else {
      Log.error(location, "args type error in function 'not'")
      Value.VOID
    }
  }

  def typecheck(args: List[Value], location: Node): Value = {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    if (args.size != 1) {
      throw new CompilerException("incorrect arguments count in call 'not'", location)
    }
    else if (args(0).isInstanceOf[org.jllvm.value.Value]) {
      s.codegen.builder.buildNot(args(0), "not")
    }
    else {
      throw new CompilerException("incorrect arguments", location)
    }
  }

  override def toString: String = {
    "not"
  }

}