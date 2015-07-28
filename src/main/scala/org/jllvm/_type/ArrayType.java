package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

/* Implements all methods for array types specified in Core.h */
public class ArrayType extends SequenceType {
	public ArrayType(Type element_type,long num_elements) {
		super(Core.LLVMArrayType(element_type.getInstance(),num_elements));
	}
	
	public ArrayType(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMArrayTypeKind);
	}
	
	public long getLength() {
		return Core.LLVMGetArrayLength(instance);
	}
}
