package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.Value;

public class RemainderInstruction extends ArithmeticInstruction {
	public enum RemainderType { FLOAT, SIGNEDINT, UNSIGNEDINT }
	
	private static LLVMOpaqueValue buildInstruction(InstructionBuilder builder,Value lhs,Value rhs,RemainderType kind,String name) {
		switch(kind) {
			case FLOAT:
				return Core.LLVMBuildFRem(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
			case SIGNEDINT:
				return Core.LLVMBuildSRem(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
			case UNSIGNEDINT:
				return Core.LLVMBuildURem(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
		}
		//This should never run.
		return null;
	}
	
	public RemainderInstruction(InstructionBuilder builder,Value lhs,Value rhs,RemainderType kind,String name) {
		super(buildInstruction(builder,lhs,rhs,kind,name),(kind == RemainderType.FLOAT));
	}
}
