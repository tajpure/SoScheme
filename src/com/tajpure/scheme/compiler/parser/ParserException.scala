package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.ast.Node

class ParserException(_message:String, _row:Int, _col:Int, _start:Int) extends Exception(_message) {
  val row:Int = _row
  val col:Int = _col
  val start:Int = _start
  
  def this(_message:String, node:Node) = this(_message, node.row, node.col, node.start)
  
  override def toString = "row:" + (row + 1) + " col:" + (col+1) + " parser error: " + getMessage()
}