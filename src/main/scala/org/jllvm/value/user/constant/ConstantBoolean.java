package org.jllvm.value.user.constant;

import java.math.BigInteger;

import org.jllvm._type.IntegerType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;

public class ConstantBoolean extends ConstantInteger {
	public ConstantBoolean(LLVMOpaqueValue c) {
		super(c);
		assert(typeOf().equals(IntegerType.i1));
	}
	
	public ConstantBoolean(boolean val) {
		super((Core.LLVMConstInt(Core.LLVMInt1Type(),BigInteger.valueOf(val ? 1 : 0),1)));
	}
}
