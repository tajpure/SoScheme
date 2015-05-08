package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.VoidList
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.IntValue
import org.jllvm.bindings.LLVMIntPredicate
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.util.Log

class CallCC extends PrimFunc("call/cc" , 1) {
  
  def apply(args: List[Value], location: Node): Value = {
     if (args.size < arity) {
      throw new CompilerException("args don't match the 'call/cc'", location)
    }
    if (args(0).isInstanceOf[IntValue] && args(1).isInstanceOf[IntValue]) {
      new BoolValue(args(0).asInstanceOf[IntValue].value == args(1).asInstanceOf[IntValue].value)
    }
    else {
      Log.error(location, "args type error in 'call/cc'")
      Value.VOID
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
    "call/cc"
  }
  
}