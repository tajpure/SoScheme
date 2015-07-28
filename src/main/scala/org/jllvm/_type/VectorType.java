package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

/* Implements all methods for vector types specified in Core.h */
public class VectorType extends SequenceType {
	public VectorType(Type element_type,long num_elements) {
		super(Core.LLVMVectorType(element_type.getInstance(),num_elements));
	}
	
	public VectorType(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMVectorTypeKind);
	}
	
	public long getSize() {
		return Core.LLVMGetVectorSize(instance);
	}
}
