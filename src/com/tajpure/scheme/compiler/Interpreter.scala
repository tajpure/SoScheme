package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser

object Interpreter extends App {
  
  def interp(_source: String): Unit = {
    Parser.parse(_source, "").interp(Scope.buildInitScope())
  }
  
  interp("(display (+ 1.0 2))")
  
}