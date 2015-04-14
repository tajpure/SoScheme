package com.tajpure.scheme.compiler.llvm

import org.jllvm.InstructionBuilder
import org.jllvm.Module
import org.jllvm.NativeLibrary
import org.jllvm._type.FunctionType
import org.jllvm._type.IdentifiedStructType
import org.jllvm._type.IntegerType
import org.jllvm._type.Type
import org.jllvm.bindings.LLVMLinkage
import org.jllvm.value.BasicBlock
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.value.user.constant.ConstantReal
import org.jllvm.value.user.instruction.ReturnInstruction
import com.tajpure.scheme.compiler.ast.Define
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.FloatType
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Func
import com.tajpure.scheme.compiler.ast.Symbol
import org.jllvm._type.PointerType
import org.jllvm.value.user.instruction.AddInstruction
import com.tajpure.scheme.compiler.ast.IntNum
import com.tajpure.scheme.compiler.ast.FloatNum
import org.jllvm.value.user.instruction.GetElementPointerInstruction
import org.jllvm.value.user.instruction.LoadInstruction

/**
 * A wrapper for LLVM API.
 */
class CodeGen(_source: String) {

  val module: Module = new Module(_source)

  val llvmTypes: LLVMTypes = new LLVMTypes()

  val builder: InstructionBuilder = new InstructionBuilder()
  
  val anyType: IdentifiedStructType = new IdentifiedStructType("AnyType")
  
  anyType.setBody(Array[org.jllvm._type.Type](
      org.jllvm._type.IntegerType.i32, 
      org.jllvm._type.IntegerType.i8, 
      org.jllvm._type.IntegerType.i32, 
      new org.jllvm._type.FloatType(),
      new org.jllvm._type.VoidType()),
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

  def buildDefine(pattern: Node, value: Node, s: Scope): org.jllvm.value.Value = {
    if (pattern.isInstanceOf[Symbol] && value.isInstanceOf[Func]) {
      val _value: Func = value.asInstanceOf[Func]
//      val _type: PointerType = new PointerType(s.codegen.anyType, 0)
      val _type: org.jllvm._type.IntegerType = org.jllvm._type.IntegerType.i32
      val _params: Array[Type] = _value.params.map {
        param => new PointerType(s.codegen.anyType, 0)
        }.toArray
      val function: org.jllvm.value.user.constant.Function = 
        new org.jllvm.value.user.constant.Function(s.codegen.module, pattern.toString(), new FunctionType(_type, _params, false))
      function.setLinkage(LLVMLinkage.LLVMExternalLinkage)
      val block: BasicBlock = function.appendBasicBlock("entry")
      _value.body.codegen(s)
      function.getParameter(0).dump()
//      val one: GetElementPointerInstruction = 
//        new GetElementPointerInstruction(s.codegen.builder, function.getParameter(0), Array(ConstantInteger.constI32(0)), "1")
//      val two: LoadInstruction = builder.buildLoad(ConstantInteger.constI32(2), "2")
      val addInstruction: AddInstruction = new AddInstruction(s.codegen.builder, ConstantInteger.constI32(1),ConstantInteger.constI32(2),false, "d")
      val two: LoadInstruction = builder.buildLoad(addInstruction, "2")
      val mul = buildMult(ConstantInteger.constI32(1),  ConstantInteger.constI32(1)) 
      s.codegen.builder.positionBuilderAtEnd(block)
      new ReturnInstruction(s.codegen.builder, addInstruction)
      function
    }
    else if (pattern.isInstanceOf[Symbol] && value.isInstanceOf[IntNum]) {
      val _pattern: Symbol = pattern.asInstanceOf[Symbol]
      val _value: Int = value.asInstanceOf[IntNum].value
      builder.buildLoad(ConstantInteger.constI32(_value), _pattern.id);
    }
    else if (pattern.isInstanceOf[Symbol] && value.isInstanceOf[FloatNum]) {
      val _pattern: Symbol = pattern.asInstanceOf[Symbol]
      val _value: Float = value.asInstanceOf[FloatNum].value
      builder.buildLoad(new ConstantReal(new org.jllvm._type.FloatType(), _value), _pattern.id);
    }
    else {
      null
    }
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