package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException

class Not extends PrimFunc("not", 1) {

  def apply(args: List[Value], location: Node): Value = {
    if (arity != args.size) {
      throw new CompilerException("Args don't match the 'not' function", location)
    }

    if (args(0).isInstanceOf[BoolValue]) {
      new BoolValue(!args(0).asInstanceOf[BoolValue].value)
    } else {
      Log.error(location, "Args type error in function 'not'")
      Value.VOID
    }
  }

  def typecheck(args: List[Value], location: Node): Value = {
    null
  }
  
  def codegen(args: List[Value], location: Node): Value = {
    null
  }

  override def toString: String = {
    "not"
  }

}