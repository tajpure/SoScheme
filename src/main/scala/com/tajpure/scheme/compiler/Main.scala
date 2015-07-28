package com.tajpure.scheme.compiler

object Main extends App {
  
  override 
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("""SoScheme v 0.0.1
-i A Scheme Interpreter
-c A Scheme Compiler
-v Version
""")
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
        println("""SoScheme v 0.0.1
-i A Scheme Interpreter
-ll Compiling scheme to llvm ir
-bc Compiling scheme to llvm bitcode
-v Version
""")
      }
    }
  }
  
}