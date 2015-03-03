package com.tajpure.scheme.compiler

object Main extends App {
  
  val interpreter: Interpreter = new Interpreter("(define a 1)")
  
  interpreter.run
  
}