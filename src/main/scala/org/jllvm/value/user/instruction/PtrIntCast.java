package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.IntegerType;
import org.jllvm._type.PointerType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class PtrIntCast extends CastInstruction {
	public enum PtrIntCastType {PTR_TO_INT,INT_TO_PTR};
	
	protected PtrIntCastType castType;
	
	public PtrIntCastType getCastType() {
		return castType;
	}
	
	public PtrIntCast(InstructionBuilder builder,Value val,Type destType,String name,PtrIntCastType type) {
		super(destType);
		castType = type;
		switch(type) {
			case PTR_TO_INT: {
				assert(destType instanceof IntegerType);
				instance = Core.LLVMBuildPtrToInt(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
			}
			case INT_TO_PTR: {
				assert(destType instanceof PointerType);
				instance = Core.LLVMBuildIntToPtr(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
				break;
			}
		}
	}
}
