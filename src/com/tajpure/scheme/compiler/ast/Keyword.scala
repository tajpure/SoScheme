package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

class Keyword(_id: String, _file: String, _start: Int, _end: Int,
    _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {
    val id: String = _id 
    
    def interp(s: Scope): Value = {
      null
    }
    
    def typeCheck(s: Scope): Value = {
      null
    }
}