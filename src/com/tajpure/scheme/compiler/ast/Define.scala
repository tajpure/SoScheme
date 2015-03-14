package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Define(pattern: Node, value: Node, _file: String, _start: Int, _end: Int,
             _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {

  def interp(s: Scope): Value = {
    null
  }

  def typeCheck(s: Scope): Value = {
    null
  }
  
  override
  def toString(): String = {
    "define"
  }

}