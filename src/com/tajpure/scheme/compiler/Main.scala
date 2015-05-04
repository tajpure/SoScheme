package com.tajpure.scheme.compiler

object Main extends App {
  
  override 
  def main(args: Array[String]) {
    val compiler: Compiler = new Compiler("./test/hello.scm")
    compiler.compile()
  }
  
}