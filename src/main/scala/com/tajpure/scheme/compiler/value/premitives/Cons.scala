package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.value.PairValue

class Cons extends PrimFunc("cons" , 2) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size != arity) {
      throw new CompilerException("args don't match the 'cons' function", location)
    }
    
    new PairValue(args(0), args(1))
 }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    null
  }

  override
  def toString: String = {
    "cons"
  }
}