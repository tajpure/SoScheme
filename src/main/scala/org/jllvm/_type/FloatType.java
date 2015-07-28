package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

public class FloatType extends RealType {
	public FloatType() {
		super(Core.LLVMFloatType());
	}
	
	public FloatType(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMFloatTypeKind);
	}
}
