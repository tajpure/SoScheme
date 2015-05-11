package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.sun.org.apache.xalan.internal.xsltc.compiler.CompilerException

class Let(val bindings: List[Node], val body: Node, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  def this(_bindings: List[Node], _body: Node, node: Node) = 
    this(_bindings, _body, node.file, node.start, node.end, node.row, node.col)
  
  def interp(s: Scope): Value = {
    val curScope = new Scope(s)
    bindings.foreach { binding => binding.interp(curScope) }
    body.interp(curScope)
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    null
  }
  
}