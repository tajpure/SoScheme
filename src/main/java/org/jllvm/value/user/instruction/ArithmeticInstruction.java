package org.jllvm.value.user.instruction;

import org.jllvm.bindings.LLVMOpaqueValue;

public class ArithmeticInstruction extends Instruction {
	private boolean fpType;
	
	public boolean isFloatingPoint() {
		return fpType;
	}
	
	public ArithmeticInstruction(LLVMOpaqueValue val,boolean fp) {
		super(val);
		fpType = fp;
	}
}
