package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.exception.RunTimeException
import com.tajpure.scheme.compiler.exception.CompilerException

class Cdr extends PrimFunc("cdr" , 1) {

  def apply(args: List[Value], location: Node): Value = {
   if (args.size != arity) {
      throw new CompilerException("args don't match the 'cdr' function", location)
    }
    
    if (args(0).isInstanceOf[PairValue]) {
      args(0).asInstanceOf[PairValue].tail
    }
    else {
      throw new RunTimeException("args type error in function 'cdr'", location)
    }
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    null
  }

  override
  def toString: String = {
    "cdr"
  }
}