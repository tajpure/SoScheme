package com.tajpure.scheme.compiler.exception

import com.tajpure.scheme.compiler.ast.Node

class RunTimeException(val message: String, val row: Int, val col: Int, val start: Int) extends Exception(message) {

  def this(_message: String, node: Node) = this(_message + ": " + node.toString(), node.row, node.col, node.start)

  def this(_message: String) = this(_message, 0, 0, 0)

  override 
  def toString = "runtime error => " + getMessage() + " on row:" + (row + 1) + " col:" + (col + 1)
  
}