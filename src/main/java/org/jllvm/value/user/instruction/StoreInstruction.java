package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.PointerType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class StoreInstruction extends Instruction {
	public StoreInstruction(InstructionBuilder builder,Value value,Value pointer) {
		super(Core.LLVMBuildStore(builder.getInstance(),value.getInstance(),pointer.getInstance()));
		assert(pointer.typeOf() instanceof PointerType);
	}
}
