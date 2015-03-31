package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node

class Display extends PrimFunc("display" , 100) {
  
  def apply(args: List[Value], location: Node): Value = {
    args.foreach { arg => println(arg) }
    Value.VOID
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }

  override
  def toString: String = {
    "display"
  }
  
}