package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Tuple(_elements: List[Node], open: Node, close: Node, _file: String, _start: Int,
            _end: Int, _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {

  val elements = _elements
  
  def interp(s: Scope): Value = {
    null
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): Value = {
    null
  }
  
  override
  def toString(): String = {
    val sb: StringBuilder = new StringBuilder(open.toString())
    for((node,i) <- elements.view.zipWithIndex) {
      sb.append(node.toString())
      if (i != elements.size - 1)
        sb.append(" ")
    }
    sb.append(close.toString())
    sb.toString()
  }
  
}