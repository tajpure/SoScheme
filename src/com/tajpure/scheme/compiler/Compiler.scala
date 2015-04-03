package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.Parser
import com.tajpure.scheme.compiler.llvm.CodeGen
import com.tajpure.scheme.compiler.util.FileUtils

class Compiler(_file: String) {
  
  val file: String  = _file
  
  def compile(): Unit = {
    val scope: Scope = Scope.buildInitCompilerScope(new CodeGen(file))
    val root = Parser.parse(_file, "")
    root.typecheck(scope)
    root.codegen(scope)
    
    val targetPath: String = FileUtils.targetPath(_file)
    scope.codegen(targetPath)
  }
  
}