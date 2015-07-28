package org.jllvm;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueModuleProvider;

public class ModuleProvider {
	protected LLVMOpaqueModuleProvider instance;
	
	public LLVMOpaqueModuleProvider getInstance() {
		return instance;
	}
	
	public ModuleProvider(Module mod) {
		instance = Core.LLVMCreateModuleProviderForExistingModule(mod.getInstance());
	}
	
	protected void finalize() {
		Core.LLVMDisposeModuleProvider(instance);
	}
}
