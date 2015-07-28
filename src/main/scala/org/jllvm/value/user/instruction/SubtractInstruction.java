package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.Value;

public class SubtractInstruction extends ArithmeticInstruction {
	private static LLVMOpaqueValue buildInstruction(InstructionBuilder builder,Value lhs,Value rhs,boolean fp,String name) {
		if(fp)
			return Core.LLVMBuildFSub(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
		else
			return Core.LLVMBuildSub(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
	}
	
	public SubtractInstruction(InstructionBuilder builder,Value lhs,Value rhs,boolean fp,String name) {
		super(buildInstruction(builder,lhs,rhs,fp,name),fp);
	}
}
