package com.tajpure.scheme.compiler

object Main extends App {
  
  override 
  def main(args: Array[String]) {
//    val compiler: Compiler = new Compiler("D:/workspaceII/SoScheme/test/double.scm")
    val compiler: Compiler = new Compiler("D:/workspace/workspace11/SoScheme/test/double.scm")
    compiler.compile()
  }
  
}