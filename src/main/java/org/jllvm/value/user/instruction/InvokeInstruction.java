package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;
import org.jllvm.value.user.constant.Function;

public class InvokeInstruction extends TerminatorInstruction {
	public InvokeInstruction(InstructionBuilder builder,Function func,Value[] arguments,BasicBlock destination,BasicBlock unwind,String name) {
		LLVMOpaqueValueRefArray argvalues = Core.new_LLVMValueRefArray(arguments.length);
		for(int i=0;i<arguments.length;i++)
			Core.LLVMValueRefArray_setitem(argvalues,i,arguments[i].getInstance());
		instance = Core.LLVMBuildInvoke(builder.getInstance(),func.getInstance(),argvalues,(long)arguments.length,destination.getBBInstance(),unwind.getBBInstance(),name);
		Core.delete_LLVMValueRefArray(argvalues);
		llvm_values.put(instance,this);
	}
}
