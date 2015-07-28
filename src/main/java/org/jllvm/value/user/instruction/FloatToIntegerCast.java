package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class FloatToIntegerCast extends CastInstruction {
	public enum FPToIntCastType {SIGNED, UNSIGNED};
	
	protected FPToIntCastType castType;
	
	public FPToIntCastType getCastType() {
		return castType;
	}
	
	public FloatToIntegerCast(InstructionBuilder builder,Value val,IntegerType destType,String name,FPToIntCastType type) {
		super(destType);
		castType = type;
		switch(type) {
			case SIGNED:
				instance = Core.LLVMBuildFPToSI(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
			case UNSIGNED:
				instance = Core.LLVMBuildFPToUI(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
		}
	}
}
