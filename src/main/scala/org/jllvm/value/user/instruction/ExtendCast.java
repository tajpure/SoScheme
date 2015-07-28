package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.RealType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class ExtendCast extends CastInstruction {
	public enum ExtendType { ZERO,SIGN,FLOAT };
	
	protected ExtendType instructionType;
	
	public ExtendType getInstructionType() {
		return instructionType;
	}
	
	public ExtendCast(ExtendType type,InstructionBuilder builder,Value val,Type destType,String name) {
		super(destType);
		assert((destType instanceof IntegerType && val.typeOf() instanceof IntegerType) ||
		       (destType instanceof RealType && val.typeOf() instanceof RealType));
		if(destType instanceof IntegerType && val.typeOf() instanceof IntegerType)
			assert(((IntegerType)destType).getWidth() > ((IntegerType)val.typeOf()).getWidth());
		instructionType = type;
		switch(type) {
			case ZERO:
				instance = Core.LLVMBuildZExt(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
			case SIGN:
				instance = Core.LLVMBuildSExt(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
			case FLOAT:
				instance = Core.LLVMBuildFPExt(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
		}
	}
}
