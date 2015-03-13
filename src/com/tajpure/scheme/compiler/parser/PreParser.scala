package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.util.FileUtils

class PreParser(_path: String) {

  val file: String = FileUtils.unifyPath(_path)
  val lexer = new LexParser(_path)
  val parser = new Parser
  
}