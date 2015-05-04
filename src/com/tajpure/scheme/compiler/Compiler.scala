package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser
import com.tajpure.scheme.compiler.llvm.CodeGen
import com.tajpure.scheme.compiler.util.FileUtils
import org.jllvm.NativeLibrary
import com.tajpure.scheme.compiler.util.Log
import java.io.IOException

class Compiler(_file: String) {
  
  val file: String  = _file
  
  def execute(): Unit = {
    NativeLibrary.load()
    
    val root = Parser.parse(file)
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    
    root.codegen(scope)
    scope.codegen.execute(scope)
  }
  
  def compile(): Unit = {
    val targetPath: String = FileUtils.target(file)
    compile(targetPath)
  }
  
  def compile(targetPath: String): Unit = {
    NativeLibrary.load()
    
    val root = Parser.parse(file)
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    
    root.codegen(scope)
    scope.save(targetPath)
    scope.codegen.print()
    Compiler.compileToBitCode(targetPath)
  }
}

object Compiler extends App {
  
  def compileToBitCode(source: String): String =  {
    val output = executeCmd("d:/llvm/llvm-as", source)
    printf(output)
    output
  }
  
  def executeBitCode(source: String): String =  {
    val output = executeCmd("d:/llvm/lli", source)
    printf(output)
    output
  }
  
  def compileToAssemblerCode(source: String): String =  {
    val output = executeCmd("d:/llvm/llc ", source)
    printf(output)
    output
  }
  
  def executeCmd(command: String, source: String): String = {
    val _command = command + " " + FileUtils.getAbsolutePath(source)
    val input = Runtime.getRuntime().exec(_command)
    val errorInfo = scala.io.Source.fromInputStream(input.getErrorStream).mkString
    printf(errorInfo)
    scala.io.Source.fromInputStream(input.getInputStream).mkString
  }
  
  try {
    val path = "./test/hello.ll"
    compileToBitCode(path)
    executeBitCode(path)
  } catch {
    case e0 : IOException => Log.error(e0.getMessage)
    case e1 : Exception => Log.error(e1.getMessage)
  }
}

