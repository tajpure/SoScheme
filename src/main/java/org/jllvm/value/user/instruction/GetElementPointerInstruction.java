package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;
import org.jllvm.value.Value;

public class GetElementPointerInstruction extends Instruction {
	public GetElementPointerInstruction(InstructionBuilder builder,Value pointer,Value indices[],String name) {
		int num_indices = indices.length;
		LLVMOpaqueValueRefArray values = Core.new_LLVMValueRefArray(num_indices);
		for(int i=0;i<indices.length;i++)
			Core.LLVMValueRefArray_setitem(values,i,indices[i].getInstance());
		instance = Core.LLVMBuildGEP(builder.getInstance(),pointer.getInstance(),values,num_indices,name);
		Core.delete_LLVMValueRefArray(values);
	}
}
