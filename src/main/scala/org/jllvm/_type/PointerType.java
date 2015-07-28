package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

/* Implements all methods for pointer types specified in Core.h */
public class PointerType extends SequenceType {
	public PointerType(Type element_type,int addressSpace) {
		super(Core.LLVMPointerType(element_type.getInstance(),addressSpace));
	}
	
	public PointerType(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMPointerTypeKind);
	}
	
	public long getAddressSpace() {
		return Core.LLVMGetPointerAddressSpace(instance);
	}
	
	public String toString() {
		return getElementType().toString() + "*";
	}
}
