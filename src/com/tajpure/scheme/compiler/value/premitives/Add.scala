package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException

class Add extends PrimFunc("+" , -1) {
  
  def apply(args: List[Value], location: Node): Value = {
    if (args.size < 2 || (arity != -1 && arity == args.size)) {
      throw new CompilerException("Args don't match the 'Add' function", location)
    }
    
    args.foldLeft(new IntValue(0).asInstanceOf[Value])((sum, arg) => {
        if (arg.isInstanceOf[IntValue] && sum.isInstanceOf[IntValue]) {
        new IntValue(arg.asInstanceOf[IntValue].value + sum.asInstanceOf[IntValue].value)
        } 
        else if (arg.isInstanceOf[FloatValue] && sum.isInstanceOf[IntValue]) {
          new FloatValue(arg.asInstanceOf[FloatValue].value + sum.asInstanceOf[IntValue].value)
        } 
        else if (arg.isInstanceOf[IntValue] && sum.isInstanceOf[FloatValue]) {
          new FloatValue(arg.asInstanceOf[IntValue].value + sum.asInstanceOf[FloatValue].value)
        } 
        else if (arg.isInstanceOf[FloatValue] && sum.isInstanceOf[FloatValue]) {
          new FloatValue(arg.asInstanceOf[FloatValue].value + sum.asInstanceOf[FloatValue].value)
        } 
        else {
          Log.error(location, "Args type error")
          Value.VOID
        }
    })
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  override
  def toString: String = {
    "+"
  }

}