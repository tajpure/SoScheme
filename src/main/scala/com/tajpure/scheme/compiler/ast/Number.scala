package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value

abstract class Number(_file: String, _start: Int, _end: Int, _row: Int, _col: Int) 
extends Node(_file, _start, _end, _row, _col) {

}