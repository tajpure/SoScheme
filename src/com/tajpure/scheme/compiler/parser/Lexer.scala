package com.tajpure.scheme.compiler

/**
 * Split source file
 */
class Lexer {
  
  // This keywords are all from R6RS(http://www.r6rs.org/final/html/r6rs/r6rs.html)
  val keywords = Array("and", 
                       "begin", 
                       "call-with-current-continuation",
                       "call-with-input-file",
                       "call-with-output-file",
                       "case",
                       "cond",
                       "define",
                       "define-syntax",
                       "delay",
                       "do",
                       "dynamic-wind",
                       "else",
                       "for-each",
                       "if",
                       "lambda", 
                       "let", 
                       "let*", 
                       "let-syntax", 
                       "letrec", 
                       "letrec-syntax", 
                       "map", 
                       "or", 
                       "syntax-rules")
  
  def apply(source: String) {
    
  }
  
}