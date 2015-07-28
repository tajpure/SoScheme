package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class BinaryBitwiseInstruction extends Instruction {
	public enum BinaryBitwiseInstructionType {AND,OR,XOR};
	
	protected BinaryBitwiseInstructionType instructionType;
	
	public BinaryBitwiseInstructionType getInstructionType() {
		return instructionType;
	} 
	
	public BinaryBitwiseInstruction(BinaryBitwiseInstructionType type,InstructionBuilder builder,Value lhs,Value rhs,String name) {
		instructionType = type;
		switch(type) {
			case AND:
				instance = Core.LLVMBuildAnd(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
				break;
			case OR:
				instance = Core.LLVMBuildOr(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
				break;
			case XOR:
				instance = Core.LLVMBuildXor(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
				break;
		}
	}
}
