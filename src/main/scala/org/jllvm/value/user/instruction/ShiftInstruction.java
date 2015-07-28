package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class ShiftInstruction extends Instruction {
	public enum ShiftType {SHL,LOGICAL_SHR,ARITHMETIC_SHR}
	
	protected ShiftType shiftType;
	
	public ShiftType getShiftType() {
		return shiftType;
	}
	
	public ShiftInstruction(ShiftType type,InstructionBuilder builder,Value lhs,Value rhs,String name) {
		shiftType = type;
		switch(type) {
			case SHL:
				instance = Core.LLVMBuildShl(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
				break;
			case LOGICAL_SHR:
				instance = Core.LLVMBuildLShr(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
				break;
			case ARITHMETIC_SHR:
				instance = Core.LLVMBuildAShr(builder.getInstance(),lhs.getInstance(),rhs.getInstance(),name);
				break;
		}
	}
}
