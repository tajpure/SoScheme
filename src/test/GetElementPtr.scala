package test

import org.jllvm.InstructionBuilder
import org.jllvm.Module
import org.jllvm._type.FunctionType
import org.jllvm._type.Type
import org.jllvm._type.IntegerType
import org.jllvm.value.user.constant.Function
import org.jllvm.bindings.LLVMLinkage
import org.jllvm.value.BasicBlock
import org.jllvm.value.user.instruction.StackAllocation
import org.jllvm._type.StructType
import org.jllvm.value.user.instruction.GetElementPointerInstruction
import org.jllvm.value.user.instruction.AddInstruction
import org.jllvm.value.Value
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.value.user.instruction.StoreInstruction
import org.jllvm.value.user.instruction.LoadInstruction
import org.jllvm.value.user.instruction.ReturnInstruction
import org.jllvm.NativeLibrary
import org.jllvm.value.user.constant.ConstantNamedStruct
import org.jllvm.value.user.constant.Constant
import org.jllvm._type.IdentifiedStructType
import org.jllvm.ExecutionEngine
import org.jllvm.generic.GenericValue

object GetElementPtr extends App {
  
  NativeLibrary.load()
  
  val elements = Array[Type](IntegerType.i64, IntegerType.i32)
  val module: Module = new Module("test")
  val builder: InstructionBuilder = new InstructionBuilder()
  val FT: FunctionType = new FunctionType(IntegerType.i32, Array(IntegerType.i32), false)
  val F: Function = new Function(module, "fac", FT)
  F.setLinkage(LLVMLinkage.LLVMExternalLinkage)
  val BB: BasicBlock = F.appendBasicBlock("entry")
  builder.positionBuilderAtEnd(BB)
  
  val structType = new IdentifiedStructType("ee")
  structType.setBody(elements, false)
  
  val alloca = new StackAllocation(builder, structType, "testHeapAllocation")
  
  val addInstruction = new AddInstruction(builder, ConstantInteger.constI32(1),ConstantInteger.constI32(2),false, "12")
  val get = new GetElementPointerInstruction(builder, alloca, Array(ConstantInteger.constI32(0), ConstantInteger.constI32(0)), "hell")
  new StoreInstruction(builder, ConstantInteger.constI32(2), get)
  val loadInstruction = new LoadInstruction(builder, get, "hell")
  
//  val addInstruction1 = new AddInstruction(builder, ConstantInteger.constI32(1), new ConstantInteger(addInstruction.getInstance),false, "")
  val add = new ConstantInteger(addInstruction.getInstance)
  add.truncate(IntegerType.i32)
  val add1 = ConstantInteger.constI32(0)
  val get1 = new GetElementPointerInstruction(builder, alloca, Array(ConstantInteger.constI32(0), add1), "hell")
  
  new ReturnInstruction(builder, loadInstruction)
  
  module.dump()
  val engine = new ExecutionEngine(module)
  val runFunction = engine.runFunction(F, new Array[GenericValue](0))
  println(org.jllvm.bindings.ExecutionEngine.LLVMGenericValueToInt(runFunction.getInstance(), 1))
  
  
}