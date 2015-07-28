package org.jllvm.value;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMAttribute;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.user.constant.Function;

public class Argument extends Value {
	public Argument() {
		instance = null;
	}
	
	public Argument(LLVMOpaqueValue val) {
		instance = val;
	}
	
	public Function getParent() {
		return Function.getFunction(Core.LLVMGetParamParent(instance));
	}
	
	public Argument getNextParameter() {
		return new Argument(Core.LLVMGetNextParam(instance));
	}
	
	public Argument getPreviousParameter() {
		return new Argument(Core.LLVMGetPreviousParam(instance));
	}
	
	public void addAttribute(LLVMAttribute attr) {
		Core.LLVMAddAttribute(instance,attr);
	}
	
	public void removeAttribute(LLVMAttribute attr) {
		Core.LLVMRemoveAttribute(instance,attr);
	}
	
	public void setParameterAlignment(long alignment) {
		Core.LLVMSetParamAlignment(instance,alignment);
	}
}
