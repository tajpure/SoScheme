package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.VectorType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class ShuffleVectorInstruction extends Instruction {
	public ShuffleVectorInstruction(InstructionBuilder builder,Value vec1,Value vec2,Value mask,String name) {
		assert(((VectorType)vec1.typeOf()).getElementType() == ((VectorType)vec2.typeOf()).getElementType() && mask.typeOf() == IntegerType.i32);
		instance = Core.LLVMBuildShuffleVector(builder.getInstance(),vec1.getInstance(),vec2.getInstance(),mask.getInstance(),name);
	}
}
