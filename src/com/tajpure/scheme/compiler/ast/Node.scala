package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.Scope

abstract class Node(_file: String, _start: Int, _end: Int, _row: Int, _col: Int) {

  val file: String = _file

  val start: Int = _start

  val end: Int = _end

  val row: Int = _row

  val col: Int = _col

  def interp(s: Scope): Value;

  def interp(node: Node, s: Scope) {
    node.interp(s)
  }

  def typeCheck(s: Scope): Value;

  def typeCheck(node: Node, s: Scope) {
    node.interp(s)
  }

}