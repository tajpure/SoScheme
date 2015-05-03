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
    interp0("D:/workspaceII/SoScheme/test/fact.scm")
    interp0("D:/workspaceII/SoScheme/test/fib.scm")
    interp0("D:/workspaceII/SoScheme/test/procedure.scm")
    interp0("D:/workspaceII/SoScheme/test/if.scm")
  }
  
  override 
  def main(args: Array[String]) {
    test()
  }
  
}