package org.jllvm.generic;

import org.jllvm._type.Type;
import org.jllvm.bindings.ExecutionEngine;

public class GenericInt extends GenericValue {
	public GenericInt(Type t,java.math.BigInteger n,boolean isSigned) {
		super(ExecutionEngine.LLVMCreateGenericValueOfInt(t.getInstance(),n,isSigned ? 1 : 0));
	}
	
	public java.math.BigInteger toInt(boolean isSigned) {
		return ExecutionEngine.LLVMGenericValueToInt(instance,isSigned ? 1 : 0);
	}
	
	public long intWidth() {
		return ExecutionEngine.LLVMGenericValueIntWidth(instance);
	}
}
