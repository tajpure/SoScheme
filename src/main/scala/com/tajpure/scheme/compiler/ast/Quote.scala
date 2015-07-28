package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.premitives.ListFunc
import com.tajpure.scheme.compiler.value.StringValue
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue

class Quote (val value: String, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {

  var quoteNode: Node = null 

  def setQuoteNode(node: Node): Unit = {
    this.quoteNode = node  
  }
  
  def interpSymbolList(nodes: List[Node], s: Scope): List[Value] = {
    nodes.map { node => {
      if (node.isInstanceOf[Number]) {
        node.interp(s)
      }
      else {
        new StringValue(node.toString())
      }
    }}
  }
  
  def interp(s: Scope): Value = {
    val valValue = if (quoteNode.isInstanceOf[Tuple]) {
        val tuple = quoteNode.asInstanceOf[Tuple]
        val args = interpSymbolList(tuple.elements, s)
        new ListFunc().apply(args, this)
      } else if (quoteNode.isInstanceOf[Symbol]) {
          new StringValue(quoteNode.toString())
      } else {
        quoteNode.interp(s)
      }
    valValue
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