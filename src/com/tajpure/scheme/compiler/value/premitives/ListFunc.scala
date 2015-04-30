package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.ListValue
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.ast.Node

class ListFunc extends PrimFunc("list" , -1) {

  def apply(args: List[Value], location: Node): Value = {
    args.foldLeft(new ListValue())((sum, arg) => {
      if (arg.isInstanceOf[ListValue]) {
        arg.asInstanceOf[ListValue].add(sum)
      }
      else {
        throw new CompilerException("incorrect arguments", location)
      }
    })
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