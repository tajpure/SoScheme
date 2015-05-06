package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.exception.RunTimeException
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Closure

class Filter extends PrimFunc("filter" , 2) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size != arity) {
      throw new CompilerException("args don't match the 'filter' function", location)
    }
    
    if (!args(0).isInstanceOf[Closure] || !args(1).isInstanceOf[PairValue]) {
      throw new RunTimeException("args type error in function 'filter'", location)
    }
    else {
      args(0).asInstanceOf[PairValue].head
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
    "filter"
  }
}