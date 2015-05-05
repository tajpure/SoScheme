package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import org.jllvm._type.FunctionType
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Type
import com.tajpure.scheme.compiler.exception.RunTimeException

class Newline extends PrimFunc("newline", 0) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size != 0) {
      throw new RunTimeException("incorrect arguments count in call 'newline'", location)
    }
    println()
    Value.VOID
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    null
  }

  override
  def toString: String = {
    "display"
  }
  
}