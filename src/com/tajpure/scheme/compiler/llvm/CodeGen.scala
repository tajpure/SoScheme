package com.tajpure.scheme.compiler.llvm

import org.jllvm.NativeLibrary

object CodeGen extends App {
  NativeLibrary.load();
  println("loaded lib file...")
}