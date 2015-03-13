package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Tuple(elements: List[Node], opne: Node, close: Node, _file: String, _start: Int,
            _end: Int, _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {

  def interp(s: Scope): Value = {
    null
  }

  def typeCheck(s: Scope): Value = {
    null
  }
  
  override
  def toString(): String = {
    val sb: StringBuilder = new StringBuilder()
    elements.foreach { node => sb.append(node.toString()) }
    sb.toString()
  }
}