package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser

object Interpreter extends App {
  
  def interp(_source: String): Unit = {
    Parser.parse(_source, "content").interp(Scope.buildInitScope())
  }
  
  def interp0(_path: String): Unit = {
    Parser.parse(_path).interp(Scope.buildInitScope())
  }
  
  override 
  def main(args: Array[String]) {
    interp(
        """
          ;(define add4
          ;(let ((x 4)))
          (display ((lambda (y) (+ 1 y)) 2))
          (newline)
          (display ((lambda x x) 3 4 5 6))
          (newline)
          (display ((lambda (x y . z) z) 3 4 5 6 7))
        """)
  }
  
}