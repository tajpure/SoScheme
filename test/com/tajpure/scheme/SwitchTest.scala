package com.tajpure.scheme

import org.jllvm.InstructionBuilder
import org.jllvm.value.user.instruction.GetElementPointerInstruction
import org.jllvm._type.FunctionType
import org.jllvm._type.IntegerType
import org.jllvm.value.user.instruction.AddInstruction
import org.jllvm.value.user.instruction.StackAllocation
import org.jllvm._type.IdentifiedStructType
import org.jllvm.value.BasicBlock
import org.jllvm.NativeLibrary
import org.jllvm.value.user.instruction.ReturnInstruction
import org.jllvm.ExecutionEngine
import org.jllvm.value.user.instruction.LoadInstruction
import org.jllvm.value.user.instruction.StoreInstruction
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.Module
import org.jllvm._type.Type
import org.jllvm.generic.GenericValue
import org.jllvm.value.user.constant.Function
import org.jllvm.bindings.LLVMLinkage

object SwitchTest extends App {

  NativeLibrary.load()
  
  val elements = Array[Type](IntegerType.i64, IntegerType.i32)
  val module: Module = new Module("test")
  val builder: InstructionBuilder = new InstructionBuilder()
  val FT: FunctionType = new FunctionType(IntegerType.i32, Array(IntegerType.i32), false)
  val F: Function = new Function(module, "fac", FT)
  F.setLinkage(LLVMLinkage.LLVMExternalLinkage)
  val BB: BasicBlock = F.appendBasicBlock("entry")
  val BB1: BasicBlock = F.appendBasicBlock("entry1")
  
  val end: BasicBlock = F.appendBasicBlock("end")
  builder.positionBuilderAtEnd(end)
  new ReturnInstruction(builder, ConstantInteger.constI32(1))
  builder.positionBuilderAtEnd(BB)
  
  val seitch = builder.buildSwitch(ConstantInteger.constI32(2), BB1, 0)
  builder.positionBuilderAtEnd(BB1)
  builder.buildBr(end)
  val BB2: BasicBlock = F.appendBasicBlock("entry2")
  builder.positionBuilderAtEnd(BB2)
  builder.buildBr(end)
  seitch.addCase(ConstantInteger.constI32(1), BB2)
  val BB3: BasicBlock = F.appendBasicBlock("entry3")
  builder.positionBuilderAtEnd(BB3)
  new ReturnInstruction(builder, ConstantInteger.constI32(2))
  seitch.addCase(ConstantInteger.constI32(2), BB3)
  
  
  module.dump()
  val engine = new ExecutionEngine(module)
  val runFunction = engine.runFunction(F, new Array[GenericValue](0))
  println(org.jllvm.bindings.ExecutionEngine.LLVMGenericValueToInt(runFunction.getInstance(), 1))
  
}