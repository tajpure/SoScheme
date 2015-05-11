package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

abstract class Node(val file: String, val start: Int, val end: Int, val row: Int, val col: Int) {

  def interp(s: Scope): Value

  def interp(node: Node, s: Scope) {
    node.interp(s)
  }

  def typecheck(s: Scope): Value

  def typecheck(node: Node, s: Scope) {
    node.interp(s)
  }
  
  def codegen(s: Scope): org.jllvm.value.Value
  
  def codegen(node: Node, s: Scope): org.jllvm.value.Value = {
    node.codegen(s)
  }
  
  def location(): String = {
    file + ": row: " + (row + 1) + " col: " + (col + 1) 
  }

}

object Node {
  
  def interpList(nodes: List[Node], s: Scope): List[Value] = {
    nodes.map { node => node.interp(s) }
  }
  
  def codegenList(nodes: List[Node], s: Scope): List[org.jllvm.value.Value] = {
    nodes.map { node => 
      node.codegen(s) 
    }
  }
  
}