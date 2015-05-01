package com.tajpure.scheme.compiler.ast

/**
 * Primitive data type
 */
abstract class PrimNode(_file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {

}