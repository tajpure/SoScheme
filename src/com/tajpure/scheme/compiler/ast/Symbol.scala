package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Symbol(_id: String, _file: String, _start: Int, _end: Int,
           _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  val id: String = _id

  def interp(s: Scope): Value = {
    s.lookup(id)
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    null
  }
  
  override
  def toString(): String = {
    id
  }
  
}

object Symbol {
  
  def genSymbol(id: String): Node = {
    new Symbol(id, null, 0, 0, 0, 0)
  }
  
}