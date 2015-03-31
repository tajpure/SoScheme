package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Block(_statements: List[Node], _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file: String, _start: Int, _end: Int, _row: Int, _col: Int) {

  val statements: List[Node] = _statements
  
  def interp(s: Scope): Value = {
    val newS: Scope = new Scope(s);
    statements.map {
      node =>
        node.interp(newS)
    }.last
    val curScope: Scope = new Scope(s)
    val values = statements.map { node => node.interp(curScope) }
    values(values.size - 1)
  }

  def typeCheck(s: Scope): Value = {
    null
  }

}