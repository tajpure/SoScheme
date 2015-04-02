package com.tajpure.scheme.compiler.llvm

import org.jllvm.InstructionBuilder
import org.jllvm.Module
import org.jllvm.NativeLibrary
import org.jllvm.value.user.constant.ConstantInteger
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Symbol
import org.jllvm.value.user.instruction.StackAllocation
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Define

/**
 * Generate LLVM IR
 */
class CodeGen(_source: String) {

  val module: Module = new Module(_source)

  val llvmTypes: LLVMTypes = new LLVMTypes()

  private val builder: InstructionBuilder = new InstructionBuilder()

  def buildAdd(lhs: org.jllvm.value.Value, rhs: org.jllvm.value.Value): org.jllvm.value.Value = {
    builder.buildAdd(lhs, rhs, "")
  }

  def buildSub(lhs: org.jllvm.value.Value, rhs: org.jllvm.value.Value): org.jllvm.value.Value = {
    builder.buildSub(lhs, rhs, "")
  }

  def builDiv(lhs: org.jllvm.value.Value, rhs: org.jllvm.value.Value): org.jllvm.value.Value = {
    builder.buildSDiv(lhs, rhs, "")
  }

  def buildMult(lhs: org.jllvm.value.Value, rhs: org.jllvm.value.Value): org.jllvm.value.Value = {
    builder.buildMul(lhs, rhs, "")
  }

  def buildDefine(define: Define): Unit = {
    
  }

  def buildConst(value: Value): org.jllvm.value.Value = {
    if (value.isInstanceOf[IntValue]) {
      ConstantInteger.constI32(value.asInstanceOf[IntValue].value)
    } else if (value.isInstanceOf[FloatValue]) {
      null
    } else {
      null
    }
  }

}

object CodeGen extends App {

  NativeLibrary.load();

  println("loaded lib file...")

}