package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.value.Value;

public class BitCast extends CastInstruction {
	public BitCast(InstructionBuilder builder,Value val,Type destType,String name) {
		super(destType);
		instance = Core.LLVMBuildBitCast(builder.getInstance(),val.getInstance(),destType.getInstance(),name);
	}
}
