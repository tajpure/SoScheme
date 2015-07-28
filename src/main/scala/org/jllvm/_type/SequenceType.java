package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueType;

/* Implements all methods specified for all sequences in Core.h */
public class SequenceType extends Type {
	public Type getElementType() {
		return Type.getType(Core.LLVMGetElementType(getInstance()));
	}

	public SequenceType(LLVMOpaqueType t) {
		super(t);
	}
}
