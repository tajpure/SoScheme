package com.tajpure.scheme.compiler.llvm

import org.jllvm.InstructionBuilder
import org.jllvm.Module
import org.jllvm.NativeLibrary
import org.jllvm._type.FunctionType
import org.jllvm._type.IntegerType
import org.jllvm._type.Type
import org.jllvm.bindings.LLVMLinkage
import org.jllvm.value.BasicBlock
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.value.user.constant.ConstantReal
import org.jllvm.value.user.instruction.StackAllocation
import com.tajpure.scheme.compiler.ast.Define
import com.tajpure.scheme.compiler.value.FloatType
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.Value
import org.jllvm.value.user.instruction.ReturnInstruction
import com.tajpure.scheme.compiler.ast.Node

/**
 * A wrapper for LLVM API.
 */
class CodeGen(_source: String) {

  val module: Module = new Module(_source)

  val llvmTypes: LLVMTypes = new LLVMTypes()

  val builder: InstructionBuilder = new InstructionBuilder()

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

  def buildDefine(pattern: Node, value: org.jllvm.value.Value): org.jllvm.value.Value = {
//    builder.buildAlloca(null, null)
    null
  }

  def buildInt(value: Value): org.jllvm.value.Value = {
    ConstantInteger.constI32(value.asInstanceOf[IntValue].value)
  }
  
  def buildFloat(value: Value): org.jllvm.value.Value = {
    new ConstantReal(new org.jllvm._type.FloatType(), value.asInstanceOf[FloatValue].value)
  }
  
  def print(): Unit = {
    module.dump()
  }
  
  def toFile(_path: String): Unit = {
    module.printToFile(_path)
  }

}

object CodeGen extends App {

  NativeLibrary.load()
  
  val codegen: CodeGen = new CodeGen("test")
  
  val _type: IntegerType = new IntegerType(32)
  val Doubles:  Array[Type] = new Array[Type](0)
  val function: org.jllvm.value.user.constant.Function = 
    new org.jllvm.value.user.constant.Function(codegen.module, "test", new FunctionType(_type, Doubles, false))
  
  function.setLinkage(LLVMLinkage.LLVMExternalLinkage)
  
  val block: BasicBlock = function.appendBasicBlock("entry");
  codegen.builder.positionBuilderAtEnd(block)
  
  new ReturnInstruction(codegen.builder, ConstantInteger.constI32(0));
  codegen.print

}