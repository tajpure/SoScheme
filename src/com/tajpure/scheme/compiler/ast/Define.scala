package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException

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
    val vValue: Value = value.interp(s)
    s.define(pattern, vValue)
    if (value.isInstanceOf[Func]) {
      val func= value.asInstanceOf[Func].codegen(pattern.toString(), s)
      s.putValue0(pattern.toString(), func)
      func
    }
    else if (value.isInstanceOf[IntNum]) {
      val intVal = value.asInstanceOf[IntNum].codegen(s)
      val pointer = s.codegen.builder.buildAlloca(intVal.typeOf(), pattern.toString())
      s.codegen.builder.buildStore(intVal, pointer)
    }
    else if (value.isInstanceOf[FloatNum]) {
      val floatVal = value.asInstanceOf[FloatNum].codegen(s)
      val pointer = s.codegen.builder.buildAlloca(floatVal.typeOf(), pattern.toString())
      s.codegen.builder.buildStore(floatVal, pointer)
    }
    else {
      throw new CompilerException("unknown value", this)
    }
  }
  
  override
  def toString(): String = {
    "define " + pattern.toString() + " " + value.toString()
  }

}