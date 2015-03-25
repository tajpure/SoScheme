package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Closure

class Call(_op: Node, _args: Argument, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  val op: Node = _op
  
  val args: Argument = _args
  
  def interp(s: Scope): Value = {
    val opValue: Value = this.op.interp(s)
    if (opValue.isInstanceOf[Closure]) {
      val closure: Closure = opValue.asInstanceOf[Closure]
      val funcScope: Scope = new Scope(closure.env)
      val params: List[Symbol] = closure.func.params
      
      if (closure.properties != null) {
        Scope.mergeDefault(closure.properties, funcScope)
      }
      
      if (1 == 0) {
        
      }
    }
    null
  }

  def typeCheck(s: Scope): Value = {
    null
  }
  
}