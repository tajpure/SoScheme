package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.Type

import org.jllvm.value.user.constant.ConstantReal

class FloatNum(val content: String, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Number(_file, _start, _end, _row, _col) {
  
  val value: Float = content.toFloat

  def interp(s: Scope): Value = {
    new FloatValue(value)
  }

  def typecheck(s: Scope): Value = {
    Type.FLOAT
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    new ConstantReal(new org.jllvm._type.FloatType(), value)
  }
  
  override
  def toString(): String = {
    content
  }
  
}