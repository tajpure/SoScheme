package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMRealPredicate;
import org.jllvm.value.Value;

public class FloatComparison extends ComparisonInstruction {
	public FloatComparison(InstructionBuilder builder,LLVMRealPredicate Op,Value lhs,Value rhs,String name) {
		instance = Core.LLVMBuildFCmp(builder.getInstance(),Op,lhs.getInstance(),rhs.getInstance(),name);
	}
}
