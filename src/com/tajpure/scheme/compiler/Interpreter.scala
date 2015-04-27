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
        (define reciprocal 
                (lambda (x) 
                  (define y (/ 3 x)) 
                  (+ x y))) 
        (display (reciprocal 2))
        """)
  }
  
}