package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.Scope
import org.jllvm.bindings.LLVMIntPredicate
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.exception.RunTimeException

class GT extends PrimFunc(">" , 2) {
  
  def apply(args: List[Value], location: Node): Value = {
    if (args.size < arity) {
      throw new CompilerException("args don't match the function '>'", location)
    }
    args(0) > args(1)
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    if (args.size != 2) {
      throw new CompilerException("incorrect arguments count in call '>'", location)
    }
    else if (args(0).isInstanceOf[org.jllvm.value.Value] && args(1).isInstanceOf[org.jllvm.value.Value]) {
      s.codegen.builder.buildICmp(LLVMIntPredicate.LLVMIntSGT, args(0), args(1), "GT")
    }
    else {
      throw new CompilerException("incorrect arguments", location)
    }
  }

  override
  def toString: String = {
    ">"
  }
  
}