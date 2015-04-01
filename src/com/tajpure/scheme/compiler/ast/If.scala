package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class If(_test: Node, _then: Node, _orelse: Node, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  def interp(s: Scope): Value = {
    null
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): Value = {
    null
  }
  
}