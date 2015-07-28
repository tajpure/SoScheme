package org.jllvm;

import org.jllvm.bindings.Core;
import org.jllvm.value.user.constant.Function;

public class FunctionPassManager extends PassManager {
	public FunctionPassManager(ModuleProvider provider) {
		instance = Core.LLVMCreateFunctionPassManager(provider.getInstance());
	}

	public FunctionPassManager(Module module) {
		instance = Core.LLVMCreateFunctionPassManagerForModule(module
				.getInstance());
	}

	public boolean initialize() {
		return (1 == Core.LLVMInitializeFunctionPassManager(instance));
	}

	public boolean run(Function function) {
		return (1 == Core.LLVMRunFunctionPassManager(instance,
				function.getInstance()));
	}

	public boolean finish() {
		return (1 == Core.LLVMFinalizeFunctionPassManager(instance));
	}

	public void finalize() {
		finish();
		Core.LLVMDisposePassManager(instance);
	}
}
