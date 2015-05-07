package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser
import com.tajpure.scheme.compiler.value.VoidValue

object Interpreter extends App {
  
  def interp(_source: String): Unit = {
    if (_source == null || _source.size == 0) {
      println("input can't be empty")
    }
    else {
      Parser.parse(_source, "REPL").interp(Scope.buildInitScope())
    }
  }
  
  def interp(_source: String, s: Scope) = {
    if (_source == null || _source.size == 0) {
      "input can't be empty"
    }
    else {
      Parser.parse(_source, "REPL").interp(s)
    }
  }
  
  def interp0(_path: String): Unit = {
    Parser.parse(_path).interp(Scope.buildInitScope())
  }
  
  def test() {
    interp0("./src/test/resources/scheme/hello.scm")
    interp0("./src/test/resources/scheme/fact.scm")
    interp0("./src/test/resources/scheme/fib.scm")
    interp0("./src/test/resources/scheme/procedure.scm")
    interp0("./src/test/resources/scheme/if.scm")
    interp0("./src/test/resources/scheme/insertsort.scm")
    interp0("./src/test/resources/scheme/quicksort.scm")
  }
  
  def repl(): Unit = {
    println("So Scheme version 0.1")
    print(">")
    var scope = Scope.buildInitScope()
    val buffer: StringBuffer = new StringBuffer
    for (line <- io.Source.stdin.getLines) {
      isExitd(line)
      buffer.append(line)
      if (isInputFinished(buffer.toString())) {
          try {
            val result = interp(buffer.toString(), scope)
            println(result)
            scope = scope.innerScope
          } catch {
            case e: Exception => println(e.getMessage) 
          }
        buffer.delete(0, buffer.length())
        print(">")
      }
    }
  } 
  
  def isInputFinished(input: String): Boolean = {
    var openCounter: Int = 0
    var closeCounter: Int = 0
    input.foreach { ch => {
      if (ch == '(') {
        openCounter = openCounter + 1
      }
      else if (ch ==')') {
        closeCounter = closeCounter + 1
      }
    } }
    if (openCounter == closeCounter) {
      true
    }
    else {
      false
    }
  }
  
  def isExitd(input: String): Unit = {
    if ("(exit)".equals(input)) {
      System.exit(0)
    }
  }
  
  override
  def main(args: Array[String]): Unit =  {
    repl()
  }
  
}