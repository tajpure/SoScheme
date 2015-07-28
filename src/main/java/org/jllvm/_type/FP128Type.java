package org.jllvm._type;

import org.jllvm.bindings.Core;

public class FP128Type extends RealType {
	public FP128Type() {
		super(Core.LLVMFP128Type());
	}
}
