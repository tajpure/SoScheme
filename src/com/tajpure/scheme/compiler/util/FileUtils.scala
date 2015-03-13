package com.tajpure.scheme.compiler.util

import java.io.File

object FileUtils {
  
  def unifyPath(path: String) : String = {
    path
  }
  
  def readFile(path: String) : String = {
    val content: String = scala.io.Source.fromFile(path).mkString
//    println(content)
    content
  }
}