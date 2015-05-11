package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser
import com.tajpure.scheme.compiler.llvm.CodeGen
import com.tajpure.scheme.compiler.util.FileUtils
import org.jllvm.NativeLibrary
import com.tajpure.scheme.compiler.util.Log
import java.io.IOException

class Compiler(val file: String) {
  
  def execute(): Unit = {
    NativeLibrary.load()
    
    val root = Parser.parse(file)
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    
    root.codegen(scope)
    scope.codegen.execute(scope)
  }
  
  /**
   * compile to LLVM IR
   */
  def compile(): Unit = {
    val targetPath: String = FileUtils.target(file)
    compile(targetPath)
  }
  
  /**
   * compile to LLVM IR
   */
  def compile(targetPath: String): Unit = {
    NativeLibrary.load()
    
    val root = Parser.parse(file)
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    
    root.codegen(scope)
    scope.save(targetPath)
    scope.codegen.print()
  }
  
  /**
   * compile to LLVM Bitcode
   */
  def compile0(): Unit = {
    val targetPath: String = FileUtils.target0(file)
    compile0(targetPath)
  }
  
  /**
   * compile to LLVM Bitcode
   */
  def compile0(bcPath: String): Unit = {
    NativeLibrary.load()
    
    val root = Parser.parse(file)
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    
    root.codegen(scope)
    scope.codegen.saveBitCode(bcPath)
    scope.codegen.print()
  }
}

object Compiler extends App {
  
  def compileToBitCode(source: String): String =  {
    val output = execute("llvm-as", source)
    println(output)
    output
  }
  
  def executeBitCode(source: String): String =  {
    val output = execute("lli", source)
    println(output)
    output
  }
  
  def compileToAssemblerCode(source: String): String =  {
    val output = execute("llc ", source)
    println(output)
    output
  }
  
  def execute(command: String, source: String): String = {
    val _command = command + " " + FileUtils.getAbsolutePath(source)
    val input = Runtime.getRuntime().exec(_command)
    val errorInfo = scala.io.Source.fromInputStream(input.getErrorStream).mkString
    println(errorInfo)
    scala.io.Source.fromInputStream(input.getInputStream).mkString
  }
  
  try {
    compileToAssemblerCode("./test/hello.ll")
  } catch {
    case e0 : IOException => Log.error(e0.getMessage)
    case e1 : Exception => Log.error(e1.getMessage)
  }
}

