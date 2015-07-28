package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class ExtractValueInstruction extends Instruction {
	public ExtractValueInstruction(InstructionBuilder builder,Value aggr,long index,String name) {
		instance = Core.LLVMBuildExtractValue(builder.getInstance(),aggr.getInstance(),index,name);
	}
}
