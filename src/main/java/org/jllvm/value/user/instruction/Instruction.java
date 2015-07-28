package org.jllvm.value.user.instruction;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.user.User;

public class Instruction extends User {
	public Instruction(LLVMOpaqueValue val) {
		instance = val;
		llvm_values.put(instance,this);
	}

	protected Instruction() {
		instance = null;
	}
	
	public BasicBlock getParent() {
		return BasicBlock.getBasicBlock(Core.LLVMGetInstructionParent(instance));
	}
	
	public Instruction getNextInstruction() {
		return new Instruction(Core.LLVMGetNextInstruction(instance));
	}
	
	public Instruction getPreviousInstruction() {
		return new Instruction(Core.LLVMGetPreviousInstruction(instance));
	}
	
	/*
	public LLVMInstruction matchInstruction(SWIGTYPE_p_LLVMOpaqueValue val) {
		assert(Core.LLVMIsAInstruction(val));
	}
	*/
}
