package com.tajpure.scheme

import org.jllvm.InstructionBuilder
import org.jllvm.value.user.instruction.GetElementPointerInstruction
import org.jllvm._type.FunctionType
import org.jllvm.value.user.constant.Function
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
import org.jllvm.bindings.LLVMLinkage

object PrintfTest extends App {

    NativeLibrary.load()
    
}