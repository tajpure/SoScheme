package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.RealType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class IntegerToFloatCast extends CastInstruction {
	public enum IntCastType {SIGNED,UNSIGNED};
	
	protected IntCastType castType;
	
	public IntCastType getCastType() {
		return castType;
	}
	
	public IntegerToFloatCast(InstructionBuilder builder,Value val,RealType destType,String name,IntCastType type) {
		super(destType);
		castType = type;
		switch(type) {
			case SIGNED:
				instance = Core.LLVMBuildSIToFP(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
			case UNSIGNED:
				instance = Core.LLVMBuildUIToFP(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
		}
	}
}
