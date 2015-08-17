package com.tajpure.scheme.compiler

object Main extends App {
  
  def printHelpInfo() {
    println("SoScheme v 0.0.1\n-i A Scheme Interpreter\n-ll Compiling scheme to llvm ir\n-bc Compiling scheme to llvm bitcode\n-v Version")
  }
  
  override 
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      printHelpInfo()
    }
    else {
      if ("-i".equals(args(0))) {
        Interpreter.repl();
      } 
      else if ("-bc".equals(args(0))) {
        if (args.length < 2) {
          println("Please input your source file!")
        }
        else {
          val compiler: Compiler = new Compiler(args(1))
          compiler.compile0()
        }
      } 
      else if ("-ll".equals(args(0))) {
        if (args.length < 2) {
          println("Please input your source file!")
        }
        else {
          val compiler: Compiler = new Compiler(args(1))
          compiler.compile()
        }
      }
      else if ("-v".equals(args(0))) {
        println("SoScheme v 0.0.1")
      } 
      else {
        printHelpInfo()
      }
    }
  }
  
}