package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.VectorType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class ExtractElementInstruction extends Instruction {
	public ExtractElementInstruction(InstructionBuilder builder,Value vector,Value index,String name) {
		assert(vector.typeOf() instanceof VectorType && index.typeOf() instanceof IntegerType);
		instance = Core.LLVMBuildExtractElement(builder.getInstance(),vector.getInstance(),index.getInstance(),name);
	}
}
