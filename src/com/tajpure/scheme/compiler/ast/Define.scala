package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.util.Log

class Define(_pattern: Node, _value: Node, _file: String, _start: Int, _end: Int,
             _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  val pattern: Node = _pattern
  
  val value: Node = _value

  def interp(s: Scope): Value = {
    val vValue: Value = value.interp(s)
    s.define(pattern, vValue)
    Value.VOID
  }

  def typecheck(s: Scope): Value = {
    val vValue: Value = value.typecheck(s)
    s.define(pattern, vValue)
    Value.VOID
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    if (value.isInstanceOf[Func]) {
      value.asInstanceOf[Func].codegen(pattern.toString(), s)
    }
    else if (value.isInstanceOf[IntNum]) {
      val intVal = value.asInstanceOf[IntNum].codegen(s)
      s.codegen.buildLoad(intVal, pattern.toString())
    }
    else if (value.isInstanceOf[FloatNum]) {
       val floatVal = value.asInstanceOf[FloatNum].codegen(s)
      s.codegen.buildLoad(floatVal, pattern.toString())
    }
    else {
      Log.error(this, "unknown value")
      null
    }
  }
  
  override
  def toString(): String = {
    "define " + pattern.toString() + " " + value.toString()
  }

}