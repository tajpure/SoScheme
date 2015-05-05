package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.value.VoidList

class ListFunc extends PrimFunc("list" , -1) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size == 0) {
      new VoidList()
    }
    else {
      args.foldRight(new VoidList().asInstanceOf[Value])(
          (arg, tail) => new PairValue(arg, tail)
          )
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
    "list"
  }
}