package org.jllvm.generic;

import org.jllvm.bindings.ExecutionEngine;
import org.jllvm.bindings.LLVMOpaqueGenericValue;

public class GenericValue {
	protected LLVMOpaqueGenericValue instance;
	
	public GenericValue(LLVMOpaqueGenericValue val) {
		instance = val;
	}
	
	protected void finalize() {
		ExecutionEngine.LLVMDisposeGenericValue(instance);
	}
	
	public LLVMOpaqueGenericValue getInstance() {
		return instance;
	}
}
