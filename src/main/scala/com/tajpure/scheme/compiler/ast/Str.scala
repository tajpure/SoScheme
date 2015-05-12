package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.StringValue
import com.tajpure.scheme.compiler.value.Type

import org.jllvm.value.user.constant.ConstantString

class Str(val value: String, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {

  def interp(s: Scope): Value = {
    new StringValue(value)
  }

  def typecheck(s: Scope): Value = {
    Type.STRING
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    val str = new ConstantString(value, true)
    val global = s.codegen.module.addGlobal(str.typeOf(), ".str")
    global.setInitializer(str)
    global
  }
  
  override
  def toString(): String = {
    "\"" + value + "\""
  }
  
}