package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMIntPredicate;
import org.jllvm.value.Value;

public class IntegerComparison extends ComparisonInstruction {
	public IntegerComparison(InstructionBuilder builder,LLVMIntPredicate Op,Value lhs,Value rhs,String name) {
		instance = Core.LLVMBuildICmp(builder.getInstance(),Op,lhs.getInstance(),rhs.getInstance(),name);
	}
}
