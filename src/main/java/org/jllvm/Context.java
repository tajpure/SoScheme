package org.jllvm;

import java.util.HashMap;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueContext;

public class Context {
	protected static HashMap<LLVMOpaqueContext,Context> llvm_contexts;
	protected LLVMOpaqueContext instance;
	
	public static Context getContext(LLVMOpaqueContext c) {
		if(llvm_contexts == null)
			llvm_contexts = new HashMap<LLVMOpaqueContext,Context>();
		Context result = llvm_contexts.get(c);
		if(result == null) {
			result = new Context(c);
			llvm_contexts.put(c,result);
		}
		assert(result != null);
		return result;
	}
	
	public Context() {
		instance = Core.LLVMContextCreate();
	}
	
	protected Context(LLVMOpaqueContext c) {
		instance = c;
	}
	
	public LLVMOpaqueContext getInstance() {
		return instance;
	}
	
	public long getMetadataKindID(String name) {
		return Core.LLVMGetMDKindIDInContext(instance,name,name.length());
	}
	
	public static Context getGlobalContext() {
		return getContext(Core.LLVMGetGlobalContext());
	}
	
	protected void finalize() {
		if(instance != Core.LLVMGetGlobalContext())
			Core.LLVMContextDispose(instance);
	}
}
