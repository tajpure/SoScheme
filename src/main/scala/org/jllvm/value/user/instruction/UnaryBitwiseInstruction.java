package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class UnaryBitwiseInstruction extends Instruction {
	public enum UnaryBitwiseInstructionType {NOT,NEGATIVE};
	
	protected UnaryBitwiseInstructionType instructionType;
	
	public UnaryBitwiseInstructionType getInstructionType() {
		return instructionType;
	}
	
	public UnaryBitwiseInstruction(UnaryBitwiseInstructionType type,InstructionBuilder builder,Value val,String name) {
		instructionType = type;
		switch(type) {
			case NOT:
				instance = Core.LLVMBuildNot(builder.getInstance(),val.getInstance(),name);
				break;
			case NEGATIVE:
				instance = Core.LLVMBuildNeg(builder.getInstance(),val.getInstance(),name);
				break;
		}
	}
} 
