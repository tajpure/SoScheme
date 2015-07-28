package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;

public class UnreachableInstruction extends TerminatorInstruction {
	public UnreachableInstruction(InstructionBuilder builder) {
		instance = Core.LLVMBuildUnreachable(builder.getInstance());
	}
}
