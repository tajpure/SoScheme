package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.exception.CompilerException

class IsPair extends PrimFunc("pair?", 1) {
  
  def apply(args: List[Value], location: Node): Value = {
    if (args.size != arity) {
      throw new CompilerException("args don't match the 'pair?' function", location)
    }
    else {
      if (args(0).isInstanceOf[PairValue]) {
        new BoolValue(true)
      } 
      /*else if (args(0).isInstanceOf[ConstValue] && args(0).asInstanceOf[ConstValue].value.isInstanceOf[PairValue]) {
          new BoolValue(true)
      }*/
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
    "pair?"
  }
  
}