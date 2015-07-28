package org.jllvm._type;

import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

public class RealType extends Type {
	public RealType(LLVMOpaqueType tr) {
		super(tr);
		LLVMTypeKind kind = getTypeKind();
		assert(kind == LLVMTypeKind.LLVMFloatTypeKind || kind == LLVMTypeKind.LLVMDoubleTypeKind || kind == LLVMTypeKind.LLVMX86_FP80TypeKind || kind == LLVMTypeKind.LLVMFP128TypeKind || kind == LLVMTypeKind.LLVMPPC_FP128TypeKind);
	}
}
