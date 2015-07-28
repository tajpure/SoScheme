package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

public class VoidType extends Type {
	public VoidType() {
		super(Core.LLVMVoidType());
	}
	
	public VoidType(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMVoidTypeKind);
	}
}
