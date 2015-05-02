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
          (define fib (lambda (n) (if (or (= n 0) (= n 1)) 1 (+ (fib (- n 1)) (fib (- n 2))))))
          ;(define (func n) (if (>= n 0) ((display (fib n) " " n) (newline) (func (- n 1)))))
          (display (fib 3))
        """)
  }
  
}