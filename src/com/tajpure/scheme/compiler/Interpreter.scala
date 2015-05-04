package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser

object Interpreter extends App {
  
  def interp(_source: String): Unit = {
    Parser.parse(_source, "content").interp(Scope.buildInitScope())
  }
  
  def interp0(_path: String): Unit = {
    Parser.parse(_path).interp(Scope.buildInitScope())
  }
  
  def test() {
    interp0("./test/fact.scm")
    interp0("./test/fib.scm")
    interp0("./test/procedure.scm")
    interp0("./test/if.scm")
  }
  
  override 
  def main(args: Array[String]) {
    // test()
    interp("""
      (display (list 0 1 2 (cons 3 4)))
      """)
  }
  
}