package com.tajpure.scheme.compiler.llvm

import org.jllvm.InstructionBuilder
import org.jllvm.Module
import org.jllvm.NativeLibrary
import org.jllvm._type.FunctionType
import org.jllvm._type.IdentifiedStructType
import org.jllvm._type.IntegerType
import org.jllvm._type.PointerType
import org.jllvm._type.Type
import org.jllvm.bindings.LLVMLinkage
import org.jllvm.value.BasicBlock
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.value.user.constant.ConstantReal
import org.jllvm.value.user.constant.Function
import org.jllvm.value.user.instruction.ReturnInstruction

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Func
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.Value

import org.jllvm.value.user.instruction.StackAllocation
import org.jllvm.value.user.instruction.GetElementPointerInstruction
import org.jllvm.value.user.instruction.LoadInstruction
import org.jllvm.value.user.instruction.StoreInstruction
import org.jllvm.value.user.instruction.PhiNode
import org.jllvm.ExecutionEngine
import org.jllvm.generic.GenericValue

/**
 * A wrapper for LLVM API.
 */
class CodeGen(_source: String) {

  val module: Module = new Module(_source)

  val llvmTypes: LLVMTypes = new LLVMTypes()

  val builder: InstructionBuilder = new InstructionBuilder()
  
//  val any: IdentifiedStructType = new IdentifiedStructType("Any")
//  
//  any.setBody(Array[org.jllvm._type.Type](
//      org.jllvm._type.IntegerType.i1, 
//      org.jllvm._type.IntegerType.i8, 
//      org.jllvm._type.IntegerType.i32, 
//      new org.jllvm._type.FloatType()),
//      false)

//  val any: IntegerType = IntegerType.i32
  val any = new org.jllvm._type.DoubleType
  
  def valueOf(alloc: StackAllocation, s: Scope): org.jllvm.value.Value = {
     null
  }
  
  def print(): Unit = {
    module.dump()
  }
  
  def save(_path: String): Unit = {
    module.printToFile(_path)
  }
  
  def saveBitCode(_path: String): Unit = {
    module.writeBitcodeToFile(_path)
  }
  
  def execute(s: Scope): Unit = {
    val curScope = s.innerScope
    val func: Function = curScope.lookupLLVM("main").asInstanceOf[Function]
    val engine = new ExecutionEngine(module)
    val runFunction = engine.runFunction(func, new Array[GenericValue](0))
    org.jllvm.bindings.ExecutionEngine.LLVMGenericValueToInt(runFunction.getInstance(), 1)
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
  codegen.print()

}