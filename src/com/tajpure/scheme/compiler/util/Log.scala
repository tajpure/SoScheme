package com.tajpure.scheme.compiler.util

import com.tajpure.scheme.compiler.ast.Node

object Log {

    def error(msg: String) {
      println(msg)
      System.exit(1)
    }
    
    def error(location:Node, msg: String) {
      println(location.location() + " " + msg)
      System.exit(1)
    }
    
    def warning(msg: String) {
      println(msg)
    }
    
    def info(msg: String) {
      println(msg)
    }
}