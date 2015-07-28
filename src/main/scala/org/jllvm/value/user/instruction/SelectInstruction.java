package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.VectorType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class SelectInstruction extends Instruction {
	public SelectInstruction(InstructionBuilder builder,Value condition,Value then,Value otherwise,String name) {
		assert(condition.typeOf() == IntegerType.i1 || (condition.typeOf() instanceof VectorType && ((VectorType)condition.typeOf()).getElementType() == IntegerType.i1));
		assert(then.typeOf() == otherwise.typeOf());
		instance = Core.LLVMBuildSelect(builder.getInstance(),condition.getInstance(),then.getInstance(),otherwise.getInstance(),name);
	}
}
