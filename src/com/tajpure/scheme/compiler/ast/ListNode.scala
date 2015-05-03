package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class ListNode(_list: List[Node], _file: String, _start: Int, _end: Int,
           _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  def this(_list: List[Node], node: Node) = this(_list, node.file, node.start, node.end, node.row, node.col)
  
  val list = _list
  
  def interp(s: Scope): Value = {
    val listValue = list.map { node => node.interp(s) }
    null
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    null
  }
  
}