package com.tajpure.scheme.compiler.util

import java.io.File

object FileUtils {
  
  def unifyPath(path: String): String = {
    path
  }
  
  def readFile(path: String): String = {
    val content: String = scala.io.Source.fromFile(path).mkString
    content
  }
  
  def targetPath(path: String): String = {
    path.substring(0, path.lastIndexOf(".")) + ".ir"
  }
  
  def main(args: Array[String]): Unit = {
    println(targetPath("/d/c/s.ss"))
  }
  
}