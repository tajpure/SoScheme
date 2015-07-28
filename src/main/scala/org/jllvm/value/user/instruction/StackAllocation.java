package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.PointerType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class StackAllocation extends AllocationInstruction {
	
	public StackAllocation(InstructionBuilder builder, Type type, Value number, String name) {
		PointerType ptrtype = new PointerType(type,0);
		instance = Core.LLVMBuildArrayAlloca(builder.getInstance(),ptrtype.getElementType().getInstance(), number.instance, name);
	}
	
	public StackAllocation(InstructionBuilder builder, Type type, String name) {
		PointerType ptrtype = new PointerType(type,0);
		instance = Core.LLVMBuildAlloca(builder.getInstance(),ptrtype.getElementType().getInstance(),name);
	}
}
