package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser
import com.tajpure.scheme.compiler.llvm.CodeGen
import com.tajpure.scheme.compiler.util.FileUtils
import org.jllvm.NativeLibrary
import com.tajpure.scheme.compiler.util.Log

class Compiler(_file: String) {
  
  val file: String  = _file
  
  def compile(): Unit = {
//    Log.info("loading native library...")
    NativeLibrary.load()
    
//    Log.info("start compiling...")
    
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    val root = Parser.parse("", _file)
//    root.typecheck(scope)
    root.codegen(scope)
    
    val targetPath: String = FileUtils.target(_file)
    
//    Log.info("saving LLVM IR code to " + targetPath + "...")
    scope.save(targetPath)
//    Log.info("finished")
    
    scope.codegen.print()
  }
  
  def run(): Unit = {
    
  }
  
}