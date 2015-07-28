package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.VectorType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class InsertElementInstruction extends Instruction {
	public InsertElementInstruction(InstructionBuilder builder,Value vector,Value element,Value index,String name) {
		assert(vector.typeOf() instanceof VectorType && index.typeOf() instanceof IntegerType && element.typeOf() == ((VectorType)vector.typeOf()).getElementType());
		instance = Core.LLVMBuildInsertElement(builder.getInstance(),vector.getInstance(),element.getInstance(),index.getInstance(),name);
	}
}
