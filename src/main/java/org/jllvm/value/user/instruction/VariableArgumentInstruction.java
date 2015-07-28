package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class VariableArgumentInstruction extends Instruction {
	public VariableArgumentInstruction(InstructionBuilder builder,Value valist,Type vatype,String name) {
		instance = Core.LLVMBuildVAArg(builder.getInstance(),valist.getInstance(),vatype.getInstance(),name);
	}
}
