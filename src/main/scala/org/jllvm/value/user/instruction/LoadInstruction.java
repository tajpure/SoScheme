package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.PointerType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class LoadInstruction extends Instruction {
	public LoadInstruction(InstructionBuilder builder,Value pointer,String name) {
		assert(pointer.typeOf() instanceof PointerType);
		instance = Core.LLVMBuildLoad(builder.getInstance(),pointer.getInstance(),name);
	}
}
