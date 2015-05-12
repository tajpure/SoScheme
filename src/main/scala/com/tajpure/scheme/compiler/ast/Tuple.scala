package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.Constants

class Tuple(val elements: List[Node], val open: Node, val close: Node, _file: String, _start: Int,
            _end: Int, _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  def this(_elements: List[Node], _open: Node, _close: Node, node: Node) = 
    this(_elements, _open, _close, node.file, node.start, node.end, node.row, node.col)
  
  def this(_elements: List[Node], tuple: Tuple) = 
    this(_elements, tuple.open, tuple.close, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
    
  def this(_elements: List[Node], node: Node) = 
    this(_elements, Name.genName(Constants.PAREN_BEGIN), Name.genName(Constants.PAREN_END), 
        node.file, node.start, node.end, node.row, node.col)
  
  def interp(s: Scope): Value = {
    elements.map { node => node.interp(s) }.last
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
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