package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser

class Compiler(_source: String) {
  
  val source: String  = _source
  
  def compile(): Unit = {
    val scope: Scope = Scope.buildInitScope()
    val root = Parser.parse(_source, "")
    root.typecheck(scope)
    root.codegen(scope)
  }
  
}