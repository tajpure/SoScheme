package com.tajpure.scheme;

import org.jllvm.InstructionBuilder;
import org.jllvm.Module;
import org.jllvm.NativeLibrary;
import org.jllvm._type.FunctionType;
import org.jllvm._type.IntegerType;
import org.jllvm._type.StructType;
import org.jllvm._type.Type;
import org.jllvm.bindings.LLVMLinkage;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;
import org.jllvm.value.user.constant.ConstantInteger;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.instruction.AddInstruction;
import org.jllvm.value.user.instruction.GetElementPointerInstruction;
import org.jllvm.value.user.instruction.LoadInstruction;
import org.jllvm.value.user.instruction.ReturnInstruction;
import org.jllvm.value.user.instruction.StackAllocation;
import org.jllvm.value.user.instruction.StoreInstruction;

public class StructTest {
    
    public static void main(String[] args) {
    	NativeLibrary.load();

		Type[] elements = new Type[2];
		elements[0] = IntegerType.i64;
		elements[1] = IntegerType.i64;

		Module module = new Module("test");
		InstructionBuilder builder = new InstructionBuilder();
		StructType structType = new StructType(elements, true);

		FunctionType FT = new FunctionType(IntegerType.i32, new Type[0], false);
		Function F = new Function(module, "fac", FT);
		F.setLinkage(LLVMLinkage.LLVMExternalLinkage);

		BasicBlock BB = F.appendBasicBlock("entry");
		builder.positionBuilderAtEnd(BB);
		StackAllocation alloca = new StackAllocation(builder, structType, "testHeapAllocation");
		new ReturnInstruction(builder, ConstantInteger.constI32(0));

	    AddInstruction addInstruction = new AddInstruction(builder, ConstantInteger.constI32(1),ConstantInteger.constI32(2),false, "");
		GetElementPointerInstruction get = new GetElementPointerInstruction(builder, alloca, new Value[]{ConstantInteger.constI32(0), ConstantInteger.constI32(0)}, "hell");
		new StoreInstruction(builder, ConstantInteger.constI32(10098), get);
		LoadInstruction loadInstruction = new LoadInstruction(builder, get, "hell");
		System.out.println(loadInstruction);
		
		module.dump();
    }

}
