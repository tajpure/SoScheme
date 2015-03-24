package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser

object Interpreter extends App {

  println("""So Scheme for compiling scheme 
to target code, depending on 
scala and llvm -version 0.0.1
  Copyright (C) 2014 tajpure""")
  
  def interp(_source: String): Unit = {
    println(_source)
    println("=> " + Parser.parse(_source, "").interp(Scope.buildInitScope()))
  }
  
  interp("(define x 1)")
}