package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.Value;

public class ReturnInstruction extends Instruction {
	protected boolean isVoid;

	public ReturnInstruction(InstructionBuilder builder) {
		this(builder, null);
	}
	
	/* Can accept null as its second parameter. */	
	public ReturnInstruction(InstructionBuilder builder,Value val) {
		if(val != null) {
			instance = Core.LLVMBuildRet(builder.getInstance(),val.getInstance());
			isVoid = false;
		}
		else {
			instance = Core.LLVMBuildRetVoid(builder.getInstance());
			isVoid = true;
		}
		llvm_values.put(instance,this);
	}

	public ReturnInstruction(LLVMOpaqueValue val) {
		super(val);
	}
	
	public boolean getIsVoid() {
		return isVoid;
	}
	
	public static ReturnInstruction getReturnInstruction(LLVMOpaqueValue val) {
		return (ReturnInstruction)Value.getValue(val);
	}
}
