package org.jllvm.generic;

import org.jllvm.bindings.ExecutionEngine;
import org.jllvm.bindings.LLVMOpaqueVoid;

public class GenericPointer extends GenericValue {
	public GenericPointer(LLVMOpaqueVoid p) {
		super(ExecutionEngine.LLVMCreateGenericValueOfPointer(p));
	}
	
	public LLVMOpaqueVoid toPointer(boolean isSigned) {
		return ExecutionEngine.LLVMGenericValueToPointer(instance);
	}
}
