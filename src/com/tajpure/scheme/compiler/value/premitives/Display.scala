package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node

class Display extends PrimFunc("display" , 1) {
  
  def apply(args: List[Value], location: Node): Value = {
    val value = args(0)
    println(value)
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