package org.jllvm.value.user.constant;

import org.jllvm.Module;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMLinkage;
import org.jllvm.bindings.LLVMVisibility;
import org.jllvm.bindings.LLVMOpaqueValue;

/* Implements every function from Core.h dealing with global values. */
public class GlobalValue extends Constant {
	public Module getParent() {
		return Module.getModule(Core.LLVMGetGlobalParent(instance));
	}
	
	public boolean isDeclaration() {
		return Core.LLVMIsDeclaration(instance) != 0;
	}
	
	public LLVMLinkage getLinkage() {
		return Core.LLVMGetLinkage(instance);
	}
	
	public void setLinkage(LLVMLinkage l) {
		Core.LLVMSetLinkage(instance,l);
	}
	
	public String getSection() {
		return Core.LLVMGetSection(instance);
	}
	
	public void setSection(String section) {
		Core.LLVMSetSection(instance,section);
	}
	
	public LLVMVisibility getVisibility() {
		return Core.LLVMGetVisibility(instance);
	}
	
	public void setVisibility(LLVMVisibility v) {
		Core.LLVMSetVisibility(instance,v);
	}
	
	public long getAlignment() {
		return Core.LLVMGetAlignment(instance);
	}
	
	public void setAlignment(long bytes) {
		Core.LLVMSetAlignment(instance,bytes);
	}
	
	public boolean isNullValue() {
		return false;
	}
	
	public GlobalValue(LLVMOpaqueValue val) {
		super(Core.LLVMIsAGlobalValue(val));
	}
}
