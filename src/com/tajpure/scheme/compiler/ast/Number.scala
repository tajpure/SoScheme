package com.tajpure.scheme.compiler.ast

class Number(_pattern: Node, _value: Node, _file: String, _start: Int, _end: Int, 
    _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
    
  val pattern: Node = _pattern
  val value: Node = _value
}