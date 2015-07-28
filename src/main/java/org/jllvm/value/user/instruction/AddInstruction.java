package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.Value;

public class AddInstruction extends ArithmeticInstruction {
	private static LLVMOpaqueValue buildInstruction(InstructionBuilder builder,Value lhs,Value rhs,boolean fp,String name) {
		if(fp)
			return Core.LLVMBuildFAdd(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
		else
			return Core.LLVMBuildAdd(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
	}
	
	public AddInstruction(InstructionBuilder builder,Value lhs,Value rhs,boolean fp,String name) {
		super(buildInstruction(builder,lhs,rhs,fp,name),fp);
	}
}
