package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Func(_params: List[Node], _propertyForm: Scope, _body: Node, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  val params: List[Node] = _params
  
  val propertyForm: Scope = _propertyForm
  
  val body: Node = _body
  
  def interp(s: Scope): Value = {
    body.interp(s)
  }

  def typeCheck(s: Scope): Value = {
    null
  }
  
  override
  def toString: String = {
    "function"
  }
  
}