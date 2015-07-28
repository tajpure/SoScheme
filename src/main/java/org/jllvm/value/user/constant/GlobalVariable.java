package org.jllvm.value.user.constant;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.Value;

public class GlobalVariable extends GlobalValue {
	public GlobalVariable(LLVMOpaqueValue c) {
		super(c);
		llvm_values.put(instance,this);
	}
	
	public static GlobalVariable getGlobalVariable(LLVMOpaqueValue c) {
		return (GlobalVariable)Value.getValue(c);
	}
	
	public GlobalVariable getNextGlobal() {
		return getGlobalVariable(Core.LLVMGetNextGlobal(instance));
	}
	
	public GlobalVariable getPreviousGlobal() {
		return getGlobalVariable(Core.LLVMGetPreviousGlobal(instance));
	}
	
	public Constant getInitializer() {
		return new Constant(Core.LLVMGetInitializer(instance));
	}
	
	public void setInitializer(Constant val) {
		Core.LLVMSetInitializer(instance,val.getInstance());
	}
	
	public boolean isThreadLocal() {
		return (Core.LLVMIsThreadLocal(instance) != 0);
	}
	
	public void setThreadLocal(boolean threadLocal) {
		Core.LLVMSetThreadLocal(instance,threadLocal ? 1 : 0);
	}
	
	public boolean isConstant() {
		return (Core.LLVMIsGlobalConstant(instance) != 0);
	}
	
	public void setConstant(boolean constant) {
		Core.LLVMSetGlobalConstant(instance,constant ? 1 : 0);
	}
	
	protected void finalize() {
		Core.LLVMDeleteGlobal(instance);
	}
}
