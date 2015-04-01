package com.tajpure.scheme.compiler.llvm

import org.jllvm.NativeLibrary
import org.jllvm.InstructionBuilder
import org.jllvm.Module

class CodeGen() {
  
  val module: Module = new Module("")
  
  val builder: InstructionBuilder = new InstructionBuilder()
  
}

object CodeGen extends App {
  
  NativeLibrary.load();
  
  println("loaded lib file...")
  
}