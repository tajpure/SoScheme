package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;

public class BranchInstruction extends TerminatorInstruction {
	public BranchInstruction(InstructionBuilder builder,BasicBlock destination) {
		instance = Core.LLVMBuildBr(builder.getInstance(),destination.getBBInstance());
		llvm_values.put(instance,this);
	}
	
	public BranchInstruction(InstructionBuilder builder,Value condition,BasicBlock thenBlock,BasicBlock elseBlock) {
		instance = Core.LLVMBuildCondBr(builder.getInstance(),condition.getInstance(),thenBlock.getBBInstance(),elseBlock.getBBInstance());
		llvm_values.put(instance,this);
	}
}
