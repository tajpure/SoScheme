package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;

public class SwitchInstruction extends TerminatorInstruction {
	public SwitchInstruction(InstructionBuilder builder,Value value,BasicBlock block,long numCases) {
		assert(numCases >= 0);
		instance = Core.LLVMBuildSwitch(builder.getInstance(),value.getInstance(),block.getBBInstance(),numCases);
		llvm_values.put(instance,this);
	}
	
	public void addCase(Value onValue,BasicBlock destination) {
		Core.LLVMAddCase(getInstance(),onValue.getInstance(),destination.getBBInstance());
	}
}
