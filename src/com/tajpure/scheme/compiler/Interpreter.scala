package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser

object Interpreter extends App {
  
  def interp(_source: String): Unit = {
    Parser.parse(_source, "REPL").interp(Scope.buildInitScope())
  }
  
  def interp(_source: String, s: Scope) = {
    Parser.parse(_source, "REPL").interp(s)
  }
  
  def interp0(_path: String): Unit = {
    Parser.parse(_path).interp(Scope.buildInitScope())
  }
  
  def test() {
    interp0("./test/hello.scm")
    interp0("./test/fact.scm")
    interp0("./test/fib.scm")
    interp0("./test/procedure.scm")
    interp0("./test/if.scm")
  }
  
  def repl(): Unit = {
    println("So Scheme version 0.1")
    print(">")
    var scope = Scope.buildInitScope()
    for (line <- io.Source.stdin.getLines) {
      try {
        println(interp(line, scope))
        scope = scope.innerScope
      } catch {
        case e: Exception => println(e.getMessage) 
      }
      print(">")
    }
  } 
  
  override 
  def main(args: Array[String]) {
    repl()
  }
  
}