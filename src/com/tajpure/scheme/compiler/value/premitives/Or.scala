package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException

class Or extends PrimFunc("or" , -1) {
  
  def apply(args: List[Value], location: Node): Value = {
    if (args.size < 2 || (arity != -1 && arity == args.size)) {
      throw new CompilerException("Args don't match the 'or' function", location)
    }
    
    args.foldLeft(new BoolValue(true).asInstanceOf[Value])((result, arg) => {
        if (result.isInstanceOf[BoolValue] && arg.isInstanceOf[BoolValue]) {
        new BoolValue(result.asInstanceOf[BoolValue].value || arg.asInstanceOf[BoolValue].value)
        }
        else {
          Log.error(location, "Args type error in function 'or'")
          Value.VOID
        }
    })
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }

  override
  def toString: String = {
    "or"
  }
  
}