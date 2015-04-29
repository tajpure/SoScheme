package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.Scope

class And extends PrimFunc("and" , -1) {
  
  def apply(args: List[Value], location: Node): Value = {
   if (args.size < 2 || (arity != -1 && arity == args.size)) {
      throw new CompilerException("Args don't match the 'and' function", location)
    }
    
    args.foldLeft(new BoolValue(true).asInstanceOf[Value])((result, arg) => {
        if (result.isInstanceOf[BoolValue]) {
          if (result.asInstanceOf[BoolValue].value) {
            arg
          }
          else {
            new BoolValue(false)
          }
        }
        else if (arg.isInstanceOf[BoolValue]) {
          if (arg.asInstanceOf[BoolValue].value) {
            result
          }
          else {
            new BoolValue(false)
          }
        }
        else if (result.isInstanceOf[IntValue] && arg.isInstanceOf[IntValue]) {
          new IntValue(result.asInstanceOf[IntValue].value & arg.asInstanceOf[IntValue].value)
        }
        else {
          Log.error(location, "Args type error in function 'and'")
          Value.VOID
        }
    })
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[Value], location: Node): Value = {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    if (args.size != 2) {
      throw new CompilerException("incorrect arguments count in call '+'", location)
    }
    else if (args(0).isInstanceOf[org.jllvm.value.Value] && args(1).isInstanceOf[org.jllvm.value.Value]) {
      s.codegen.builder.buildAdd(args(0), args(1), "add")
    }
    else {
      throw new CompilerException("incorrect arguments", location)
    }
  }

  override
  def toString: String = {
    "and"
  }
  
}