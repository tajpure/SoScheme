package org.jllvm;

import org.jllvm.bindings.Core;

public class ModulePassManager extends PassManager {
	public ModulePassManager() {
		instance = Core.LLVMCreatePassManager();
	}
	
	public boolean run(Module val) {
		return (Core.LLVMRunPassManager(instance,val.getInstance()) == 1);
	}
}
