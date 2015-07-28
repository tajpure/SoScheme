package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.RealType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class TruncateInstruction extends CastInstruction {
	public enum TruncateType { FLOAT,INTEGER };
	
	protected TruncateType instructionType;
	
	public TruncateType getInstructionType() {
		return instructionType;
	}
	
	public TruncateInstruction(TruncateType type,InstructionBuilder builder,Value val,Type destType,String name) {
		super(destType);
		instructionType = type;
		switch(type) {
			case FLOAT: {
				assert(val.typeOf() instanceof RealType);
				assert(destType instanceof RealType);
				instance = Core.LLVMBuildFPTrunc(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
			}
			case INTEGER: {
				assert(val.typeOf() instanceof IntegerType);
				assert(destType instanceof IntegerType);
				instance = Core.LLVMBuildTrunc(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
			}
		}
	}
}
