package org.jllvm._type;

import org.jllvm.bindings.Core;

public class DoubleType extends RealType {
	public DoubleType() {
		super(Core.LLVMDoubleType());
	}
}
