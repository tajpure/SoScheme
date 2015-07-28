package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class HeapAllocation extends AllocationInstruction {
	
	public HeapAllocation(InstructionBuilder builder,Type type,Value number,String name) {
		if(number != null) {
			assert(number.typeOf() == IntegerType.i32);
			instance = Core.LLVMBuildArrayMalloc(builder.getInstance(),type.getInstance(),number.getInstance(),name);
		} else {
			instance = Core.LLVMBuildMalloc(builder.getInstance(),type.getInstance(),name);
		}
	}
	
	public HeapAllocation(InstructionBuilder builder, Type type, String name) {
		instance = Core.LLVMBuildMalloc(builder.getInstance(),type.getInstance(),name);
	}
}
