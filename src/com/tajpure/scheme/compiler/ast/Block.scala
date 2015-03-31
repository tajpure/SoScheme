package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Block(_statements: List[Node], _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file: String, _start: Int, _end: Int, _row: Int, _col: Int) {

  val statements: List[Node] = _statements
  
  def interp(s: Scope): Value = {
    val curScope: Scope = new Scope(s)
    val values = statements.map { node => node.interp(curScope) }
    values(values.size - 1)
  }

  def typeCheck(s: Scope): Value = {
    val curScope: Scope = new Scope(s)
    val values = statements.map { node => node.typeCheck(curScope) }
    values(values.size - 1)
  }
  
  override
  def toString(): String = {
    statements.foldLeft("")((content: String, node: Node) => content + node.toString())
  }

}

object Block extends App {
  val statements: List[Int] = List(1,23,3,45,6)
  println(statements.foldLeft("")((content: String, node: Int) => content + node.toString()))
  var i = 1
  statements.map { r => i = r }
  println(i)
}