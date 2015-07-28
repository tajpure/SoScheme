package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

public class PPCFP128Type extends RealType {
	public PPCFP128Type() {
		super(Core.LLVMPPCFP128Type());
	}
	
	public PPCFP128Type(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMPPC_FP128TypeKind);
	}
}
