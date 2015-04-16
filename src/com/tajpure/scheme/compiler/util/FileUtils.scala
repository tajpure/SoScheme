package com.tajpure.scheme.compiler.util

import java.io.File
import java.io.FileNotFoundException

object FileUtils {
  
  def unifyPath(path: String): String = {
    path
  }
  
  def read(path: String): String = {
    try {
      scala.io.Source.fromFile(path).mkString
    } catch {
      case e0 : FileNotFoundException => Log.error(e0.getMessage); null
      case e1 : Exception => Log.error(e1.getMessage); null
    }
  }
  
  def target(path: String): String = {
    path.substring(0, path.lastIndexOf(".")) + ".ll"
  }
  
  def main(args: Array[String]): Unit = {
    println(target("/d/c/s.ss"))
  }
  
}