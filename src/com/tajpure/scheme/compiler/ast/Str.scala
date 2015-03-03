package com.tajpure.scheme.compiler.ast

class Str(_value: String, _file: String, _start: Int, _end: Int, _row: Int, _col: Int) 
extends Node(_file, _start, _end, _row, _col) {
  
  val value = _value
  
}