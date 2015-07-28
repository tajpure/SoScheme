package org.jllvm.value;

import org.jllvm._type.Type;
import org.jllvm.bindings.Core;

public class UndefinedValue extends Value {
	public UndefinedValue(Type t) {
		instance = Core.LLVMGetUndef(t.getInstance());
	}
}
