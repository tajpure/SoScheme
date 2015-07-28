package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class InsertValueInstruction extends Instruction {
	public InsertValueInstruction(InstructionBuilder builder,Value aggr,Value Value,long index,String name) {
		//assert(aggr.typeOf() instanceof LLVMaggrType && Value.typeOf() == ((LLVMaggrType)aggr.typeOf()).getValueType());
		instance = Core.LLVMBuildInsertValue(builder.getInstance(),aggr.getInstance(),Value.getInstance(),index,name);
	}
}
