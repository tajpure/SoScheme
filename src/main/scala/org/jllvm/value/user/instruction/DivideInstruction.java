package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.Value;

public class DivideInstruction extends ArithmeticInstruction {
	public enum DivisionType { FLOAT, SIGNEDINT, UNSIGNEDINT };
	
	private static LLVMOpaqueValue buildInstruction(InstructionBuilder builder,Value lhs,Value rhs,DivisionType kind,String name) {
		switch(kind) {
			case FLOAT:
				return Core.LLVMBuildFDiv(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
			case SIGNEDINT:
				return Core.LLVMBuildSDiv(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
			case UNSIGNEDINT:
				return Core.LLVMBuildUDiv(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
		}
		//This should never run.
		return null;
	}
	
	public DivideInstruction(InstructionBuilder builder,Value lhs,Value rhs,DivisionType kind,String name) {
		super(buildInstruction(builder,lhs,rhs,kind,name),(kind == DivisionType.FLOAT));
	}
}
