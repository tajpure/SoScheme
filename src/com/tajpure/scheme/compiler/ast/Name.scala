package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

object Name {
  
  def genName(id: String): Node = {
    new Name(id, null, 0, 0, 0, 0)
  }
}

class Name(_id: String, _file: String, _start: Int, _end: Int,
           _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  val id: String = _id

  def interp(s: Scope): Value = {
    null
  }

  def typeCheck(s: Scope): Value = {
    null
  }
  
  override
  def toString(): String = {
    id + "=>Name"
  }
}