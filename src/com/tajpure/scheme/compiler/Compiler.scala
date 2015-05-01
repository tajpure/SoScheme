package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser
import com.tajpure.scheme.compiler.llvm.CodeGen
import com.tajpure.scheme.compiler.util.FileUtils
import org.jllvm.NativeLibrary
import com.tajpure.scheme.compiler.util.Log

class Compiler(_file: String) {
  
  val file: String  = _file
  
  def compile(): Unit = {
    val targetPath: String = FileUtils.target(file)
    compile(targetPath)
  }
  
  def compile(targetPath: String): Unit = {
    
    NativeLibrary.load()
    
    val root = Parser.parse(file)
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    
//    root.typecheck(scope)
    root.codegen(scope)
    
    scope.save(targetPath)
    scope.codegen.print()
  }
  
  def run(): Unit = {
    
  }
  
}