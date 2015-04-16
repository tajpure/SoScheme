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

/**
 * A wrapper for LLVM API.
 */
class CodeGen(_source: String) {

  val module: Module = new Module(_source)

  val llvmTypes: LLVMTypes = new LLVMTypes()

  val builder: InstructionBuilder = new InstructionBuilder()
  
  val any: IdentifiedStructType = new IdentifiedStructType("Any")
  
  any.setBody(Array[org.jllvm._type.Type](
      org.jllvm._type.IntegerType.i32, 
      org.jllvm._type.IntegerType.i8, 
      org.jllvm._type.IntegerType.i32, 
      new org.jllvm._type.FloatType()),
      false)

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
  
  def buildFunc(pattern: Node, value: Node, s: Scope): org.jllvm.value.Value = {
      val _value: Func = value.asInstanceOf[Func]
      val _type: PointerType = new PointerType(s.codegen.any, 0)
      val _params: Array[Type] = _value.params.map { param => new PointerType(s.codegen.any, 0) }.toArray
      val function: Function = new Function(s.codegen.module, pattern.toString(), new FunctionType(_type, _params, false))
      
      function.setLinkage(LLVMLinkage.LLVMExternalLinkage)
      s.put("this", "function", function)
      
      val block: BasicBlock = function.appendBasicBlock("entry")
      builder.positionBuilderAtEnd(block)
      val last = _value.body.codegen(s)
      s.codegen.builder.buildRet(last)
      function
  }
  
  def valueOf(alloc: StackAllocation, s: Scope): org.jllvm.value.Value = {
     val typeIndeces = Array[org.jllvm.value.Value](ConstantInteger.constI32(0), ConstantInteger.constI32(0))
     val typePtr: GetElementPointerInstruction = builder.buildGEP(alloc, typeIndeces, "typeP")
     val typeVal: LoadInstruction = builder.buildLoad(typePtr, "typeVal")
     
     val curFunc: Function = s.lookupPropertyLocal("this", "function").asInstanceOf[Function]
     val switchEntry: BasicBlock = curFunc.appendBasicBlock("switch_entry")
     val switchEnd: BasicBlock = curFunc.appendBasicBlock("switch_end")
     val switchDefault: BasicBlock = curFunc.appendBasicBlock("switch_default")
     val block1: BasicBlock = curFunc.appendBasicBlock("block1")
     val block2: BasicBlock = curFunc.appendBasicBlock("block2")
     val block3: BasicBlock = curFunc.appendBasicBlock("block3")
     
     builder.positionBuilderAtEnd(switchEntry)
     val switch = builder.buildSwitch(typeVal, block1, 3)
     switch.addCase(ConstantInteger.constI32(1), block1)
     switch.addCase(ConstantInteger.constI32(2), block2)
     switch.addCase(ConstantInteger.constI32(3), block3)
     
     builder.positionBuilderAtEnd(switchDefault)
     builder.buildBr(switchEnd)
     
     builder.positionBuilderAtEnd(block1)
     val valueIndeces1 = Array[org.jllvm.value.Value](ConstantInteger.constI32(0), ConstantInteger.constI32(1))
     val valuePtr1: GetElementPointerInstruction = builder.buildGEP(alloc, valueIndeces1, "valuePtr1")
     val valueVal1: LoadInstruction = builder.buildLoad(valuePtr1, "valueVal1")
     builder.buildBr(switchEnd)
     
     builder.positionBuilderAtEnd(block2)
     val valueIndeces2 = Array[org.jllvm.value.Value](ConstantInteger.constI32(0), ConstantInteger.constI32(2))
     val valuePtr2: GetElementPointerInstruction = builder.buildGEP(alloc, valueIndeces2, "valuePtr2")
     val valueVal2: LoadInstruction = builder.buildLoad(valuePtr2, "valueVal2")
     builder.buildBr(switchEnd)
     
     builder.positionBuilderAtEnd(block3)
     val valueIndeces3 = Array[org.jllvm.value.Value](ConstantInteger.constI32(0), ConstantInteger.constI32(3))
     val valuePtr3: GetElementPointerInstruction = builder.buildGEP(alloc, valueIndeces3, "valuePtr3")
     val valueVal3: LoadInstruction = builder.buildLoad(valuePtr3, "valueVal3")
     builder.buildBr(switchEnd)
     
     builder.positionBuilderAtEnd(switchEnd)
     
     val res = new PhiNode(builder, IntegerType.i32, "result")
     val phiVals = Array[org.jllvm.value.Value](valueVal1, valueVal2, valueVal3)
     val phiBlocks = Array(block1, block2, block3)
     res.addIncoming(phiVals, phiBlocks)
     
     res
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
  
  def save(_path: String): Unit = {
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
  codegen.print()

}