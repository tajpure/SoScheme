package com.tajpure.scheme.compiler.util

import java.io.File

object FileUtils {
  
  def unifyPath(path: String) : String = {
    path
  }
  
  def readFile(path: String) : String = {
    scala.io.Source.fromFile(path).mkString
  }
}