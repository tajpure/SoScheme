package com.tajpure.scheme.compiler.util

import java.io.File
import java.io.FileNotFoundException
import com.tajpure.scheme.compiler.exception.ParserException

object FileUtils {
  
  def unifyPath(path: String): String = {
    path
  }
  
  def read(path: String): String = {
    try {
      if (path == null || path.size == 0) {
        throw new ParserException("incorrect path")
      }
      scala.io.Source.fromFile(path).mkString
    } catch {
      case e0 : FileNotFoundException => throw e0
      case e1 : Exception => throw e1
    }
  }
  
  def getAbsolutePath(path: String): String = {
    val file = new File(path)
    file.getAbsolutePath
  }
  
  def target(path: String): String = {
    path.substring(0, path.lastIndexOf(".")) + ".ll"
  }
  
  def target0(path: String): String = {
    path.substring(0, path.lastIndexOf(".")) + ".bc"
  }
  
  def main(args: Array[String]): Unit = {
    println(target("/d/c/s.ss"))
  }
  
}