package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import org.jllvm._type.PointerType
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import org.jllvm.value.user.constant.ConstantReal
import org.jllvm._type.DoubleType
import com.tajpure.scheme.compiler.value.Closure

class Name(_id: String, _file: String, _start: Int, _end: Int,
           _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
  
  val id: String = _id

  def interp(s: Scope): Value = {
    s.lookup(id)
  }

  def typecheck(s: Scope): Value = {
    s.lookup(id)
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    val value: Value = s.lookup(id)
    if (value != null) {
       if (value.isInstanceOf[IntValue]) {
         s.codegen.buildInt(value)
       }
       else if (value.isInstanceOf[FloatValue]) {
         s.codegen.buildFloat(value)
       }
       else if (value.isInstanceOf[Closure]) {
         s.lookup0(id)
       }
       else {
         null
       }
    }
    else {
      s.lookupParameter(id).asInstanceOf[org.jllvm.value.Argument]
    }
  }
  
  override
  def toString(): String = {
    id
  }
  
}

object Name {
  
  def genName(id: String): Node = {
    new Name(id, null, 0, 0, 0, 0)
  }
  
}