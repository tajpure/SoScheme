package com.tajpure.scheme.compiler

object Main extends App {
  
  override 
  def main(args: Array[String]): Unit = {
    val compiler: Compiler = new Compiler("./src/test/resources/scheme/double.scm")
    compiler.compile0()
  }
  
}