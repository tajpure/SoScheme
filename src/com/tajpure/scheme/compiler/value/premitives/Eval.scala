package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node

class Eval extends PrimFunc("eval" , 1) {

  def apply(args: List[Value], location: Node): Value = {
    null
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }

  override
  def toString: String = {
    "eval"
  }
  
}