package com.tajpure.scheme.compiler.llvm

import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Type
import com.tajpure.scheme.compiler.value.IntValue

class LLVMTypes {
  
  def convert(value: Value): org.jllvm._type.Type = {
    if (value.isInstanceOf[IntValue]) {
      null
    } else {
      null
    }
  }

}