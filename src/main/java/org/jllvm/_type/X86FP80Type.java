package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

public class X86FP80Type extends RealType {
	public X86FP80Type() {
		super(Core.LLVMX86FP80Type());
	}
	
	public X86FP80Type(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMX86_FP80TypeKind);
	}
}
