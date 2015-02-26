package com.tajpure.scheme.compiler.ast

abstract class Node(_file:String, _start:Int, _end:Int, _row:Int, _col:Int) {
  val file:String = _file
  val start:Int = _start
  val end:Int = _end
  val row:Int = _row
  val col:Int = _col
  
}