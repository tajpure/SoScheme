package org.jllvm.generic;

import org.jllvm._type.RealType;
import org.jllvm.bindings.ExecutionEngine;

public class GenericReal extends GenericValue {
	public GenericReal(RealType t,double n) {
		super(ExecutionEngine.LLVMCreateGenericValueOfFloat(t.getInstance(),n));
	}
	
	public double toReal(RealType t) {
		return ExecutionEngine.LLVMGenericValueToFloat(t.getInstance(),instance);
	}
}
