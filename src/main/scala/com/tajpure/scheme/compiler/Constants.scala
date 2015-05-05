package com.tajpure.scheme.compiler

object Constants {

  // delimiters and delimiters pairs
  val PAREN_BEGIN = "("
  
  val PAREN_END = ")"
  
  val SQUARE_BEGIN = "["
  
  val SQUARE_END = "]"

  val STRING_BEGIN = "\""
  
  val STRING_END = "\""

  // Quotation
  val _QUOTE = "quote"
  
  val QUOTE = '''
  
  val QUOTE_NEAR = "`"

  // Comments
  val COMMENTS = ";"
  
  val COMMENTS_DATUM = "#;"
  
  val COMMENTS_BLOCK_BEGIN = "#|"
  
  val COMMENTS_BLOCK_END = "|#"
  
  val CHAR_PREFIX = "#\\"
  
  // Pair
  val DOT = "."

  // This keywords are all from R6RS(http://www.r6rs.org/final/html/r6rs/r6rs.html)
  val AND = "and"
  
  val BEGIN = "begin"
  
  val CWCC = "call-with-current-continuation"
  
  val CWIF = "call-with-input-file"
  
  val CWOF = "call-with-output-file"
  
  val CASE = "case"
  
  val COND = "cond"
  
  val DEFINE = "define"
  
  val DEFINE_SYNTAX = "define-syntax"
  
  val DELAY = "delay"
  
  val DO = "do"
  
  val DYNAMIC_WIND = "dynamic-wind"
  
  val ELSE = "else"
  
  val FOR_EACH = "for-each"
  
  val IF = "if"
  
  val LAMBDA = "lambda"
  
  val LET = "let"
  
  val LET_STAR = "let*"
  
  val LET_SYNTAX = "let-syntax"
  
  val LETREC = "letrec"
  
  val LETREC_SYNTAX = "LETREC_SYNTAX"
  
  val MAP = "map"
  
  val OR = "or"
  
  val SYNTAX_RULES = "syntax-rules"

  val KEYWORDS = List(AND, BEGIN, CWCC, CWOF, CASE, COND, DEFINE, DEFINE_SYNTAX, DELAY, DO,
    DYNAMIC_WIND, ELSE, FOR_EACH, IF, LAMBDA, LET, LET_STAR, LET_SYNTAX, LETREC, LETREC_SYNTAX, MAP, OR, SYNTAX_RULES)
  
  // extends char ! $ % & * + - . / : < = > ? @ ^ _ ~
    
  val IDENT_CHARS = List('!', '$', '%', '&', '*', '/', ':', '<', '=', '>', '?', '^', '_',
                    '~', '+', '-', '@', '.', '#')
                    
}