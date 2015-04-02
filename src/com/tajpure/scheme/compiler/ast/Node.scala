package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

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

  def typecheck(s: Scope): Value;

  def typecheck(node: Node, s: Scope) {
    node.interp(s)
  }
  
  def codegen(s: Scope): org.jllvm.value.Value;
  
  def location(): String = {
    file + ": row: " + (row + 1) + " col: " + (col + 1) 
  }

}

object Node {
  
  def interpList(nodes: List[Node], s: Scope): List[Value] = {
    nodes.map { node => node.interp(s) }
  }
  
  def codegenList(nodes: List[Node], s: Scope): List[org.jllvm.value.Value] = {
    nodes.map { node => node.codegen(s) }
  }
  
}