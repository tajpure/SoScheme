package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.CharValue
import com.tajpure.scheme.compiler.exception.CompilerException
import org.jllvm.value.user.constant.ConstantInteger


class CharNum ( val value: String, _file: String, _start: Int, _end: Int,
             _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  def interp(s: Scope): Value = {
    new CharValue(value.substring(2))
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    ConstantInteger.constI8(toAscii(value))
  }
  
  def toAscii(value: String): Int = {
    value.substring(2) match {
      case "nul" => 0
      case "tab" => 9
      case "vtab" => 11
      case "return" => 13
      case "esc" => 27
      case "space" => 32
      case "delete" => 127
      case _ => value.substring(2)(0).toInt
    }
  }
  
  override
  def toString(): String = {
    value
  }
  
}