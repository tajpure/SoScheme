package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.PointerType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class FreeInstruction extends Instruction {
	public FreeInstruction(InstructionBuilder builder,Value pointerValue) {
		assert(pointerValue.typeOf() instanceof PointerType);
		instance = Core.LLVMBuildFree(builder.getInstance(),pointerValue.getInstance());
	}
}
