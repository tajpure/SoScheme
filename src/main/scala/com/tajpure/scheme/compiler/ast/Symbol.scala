package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node;
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.ConstValue

class Symbol (_value: String, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {

  val value = _value
  
  var quoteNode: Node = null 

  def setQuoteNode(node: Node): Unit = {
    this.quoteNode = node  
  }
  
  def interp(s: Scope): Value = {
    new ConstValue(quoteNode.toString())
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    null
  }
  
  override
  def toString(): String = {
    value + quoteNode
  }
  
}