package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.ParserException

/**
 * Split source file
 */
class Lexer(_source: String) {
  
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
  var offset:Int = -1
  var row:Int = -1
  var col:Int = -1
  val source:String = _source
  
  def forward() {
    if(source.charAt(offset) != '\n') {
      col += 1
    } else {
      row += 1
      col = 0
    }
    offset += 1
  }
  
  def skip(n:Int) {
    for (i <- 1 to n) {
      forward()
    }
  }
  
  
  @throws(classOf[ParserException])
  def tokenize() {
    
  }
}