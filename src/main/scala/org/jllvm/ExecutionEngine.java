package org.jllvm;

import org.jllvm.bindings.LLVMOpaqueExecutionEngine;
import org.jllvm.bindings.LLVMOpaqueGenericValue;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.bindings.LLVMOpaqueExecutionEngineRefArray;
import org.jllvm.bindings.LLVMOpaqueGenericValueRefArray;
import org.jllvm.bindings.LLVMOpaqueModuleRefArray;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;
import org.jllvm.bindings.LLVMOpaqueStringRefArray;
import org.jllvm.bindings.LLVMOpaqueVoid;
import org.jllvm.generic.GenericValue;
import org.jllvm.value.Value;
import org.jllvm.value.user.constant.Function;

public class ExecutionEngine {
	protected LLVMOpaqueExecutionEngine instance;

	public ExecutionEngine(Module mod) throws Exception {
		LLVMOpaqueExecutionEngineRefArray engines = org.jllvm.bindings.ExecutionEngine
				.new_LLVMExecutionEngineRefArray(1);
		LLVMOpaqueStringRefArray outerrs = org.jllvm.bindings.ExecutionEngine
				.new_StringArray(1);
		boolean success = org.jllvm.bindings.ExecutionEngine
				.LLVMCreateExecutionEngineForModule(engines, mod.getInstance(),
						outerrs) == 0;
		String outerr = org.jllvm.bindings.ExecutionEngine.StringArray_getitem(
				outerrs, 0);
		org.jllvm.bindings.ExecutionEngine.delete_StringArray(outerrs);
		outerrs = null;
		instance = org.jllvm.bindings.ExecutionEngine
				.LLVMExecutionEngineRefArray_getitem(engines, 0);
		org.jllvm.bindings.ExecutionEngine
				.delete_LLVMExecutionEngineRefArray(engines);
		engines = null;
		if (!success)
			throw new Exception(outerr);
	}

	public static void linkInJit() {
		org.jllvm.bindings.ExecutionEngine.LLVMLinkInJIT();
	}

	public static void linkInInterpreter() {
		org.jllvm.bindings.ExecutionEngine.LLVMLinkInInterpreter();
	}

	public LLVMOpaqueExecutionEngine getInstance() {
		return instance;
	}

	public void runStaticConstructors() {
		org.jllvm.bindings.ExecutionEngine.LLVMRunStaticConstructors(instance);
	}

	public void runStaticDestructors() {
		org.jllvm.bindings.ExecutionEngine.LLVMRunStaticDestructors(instance);
	}

	public int runFunctionAsMain(Function f, String[] argv, String[] envp) {
		LLVMOpaqueStringRefArray args = org.jllvm.bindings.ExecutionEngine
				.new_StringArray(argv.length);
		LLVMOpaqueStringRefArray envs = org.jllvm.bindings.ExecutionEngine
				.new_StringArray(envp.length);
		for (int i = 0; i < argv.length; i++)
			org.jllvm.bindings.ExecutionEngine.StringArray_setitem(args, i,
					argv[i]);
		for (int i = 0; i < envp.length; i++)
			org.jllvm.bindings.ExecutionEngine.StringArray_setitem(envs, i,
					envp[i]);
		int result = org.jllvm.bindings.ExecutionEngine.LLVMRunFunctionAsMain(
				instance, f.getInstance(), argv.length, args, envs);
		org.jllvm.bindings.ExecutionEngine.delete_StringArray(envs);
		org.jllvm.bindings.ExecutionEngine.delete_StringArray(args);
		return result;
	}

	public GenericValue runFunction(Function f, GenericValue[] args) {
		LLVMOpaqueGenericValueRefArray arg_array = org.jllvm.bindings.ExecutionEngine
				.new_LLVMGenericValueRefArray(args.length);
		for (int i = 0; i < args.length; i++)
			org.jllvm.bindings.ExecutionEngine
					.LLVMGenericValueRefArray_setitem(arg_array, i,
							args[i].getInstance());
		LLVMOpaqueGenericValue res = org.jllvm.bindings.ExecutionEngine
				.LLVMRunFunction(instance, f.getInstance(), args.length,
						arg_array);
		org.jllvm.bindings.ExecutionEngine
				.delete_LLVMGenericValueRefArray(arg_array);
		return new GenericValue(res);
	}

	public void freeMachineCodeForFunction(Function f) {
		org.jllvm.bindings.ExecutionEngine.LLVMFreeMachineCodeForFunction(
				instance, f.getInstance());
	}

	public void addModule(Module m) {
		org.jllvm.bindings.ExecutionEngine.LLVMAddModule(instance,
				m.getInstance());
	}

	public boolean removeModule(Module m) {
		LLVMOpaqueStringRefArray outerr = org.jllvm.bindings.ExecutionEngine
				.new_StringArray(1);
		LLVMOpaqueModuleRefArray outmod = org.jllvm.bindings.ExecutionEngine
				.new_LLVMModuleRefArray(1);
		boolean result = org.jllvm.bindings.ExecutionEngine.LLVMRemoveModule(
				instance, m.getInstance(), outmod, outerr) > 0;
		org.jllvm.bindings.ExecutionEngine.delete_LLVMModuleRefArray(outmod);
		org.jllvm.bindings.ExecutionEngine.delete_StringArray(outerr);
		return result;
	}

	public Function findFunction(String name) {
		LLVMOpaqueValueRefArray resarray = org.jllvm.bindings.ExecutionEngine
				.new_LLVMValueRefArray(1);
		boolean success = org.jllvm.bindings.ExecutionEngine.LLVMFindFunction(
				instance, name, resarray) > 0;
		LLVMOpaqueValue result = org.jllvm.bindings.ExecutionEngine
				.LLVMValueRefArray_getitem(resarray, 0);
		org.jllvm.bindings.ExecutionEngine.delete_LLVMValueRefArray(resarray);
		if (success && result != null)
			return new Function(result);
		else
			return null;
	}

	public TargetData getTargetData() {
		return new TargetData(
				org.jllvm.bindings.ExecutionEngine
						.LLVMGetExecutionEngineTargetData(instance));
	}

	public void addGlobalMapping(Value v, LLVMOpaqueVoid addr) {
		org.jllvm.bindings.ExecutionEngine.LLVMAddGlobalMapping(instance,
				v.getInstance(), addr);
	}

	public LLVMOpaqueVoid getPointerToGlobal(Value v) {
		return org.jllvm.bindings.ExecutionEngine.LLVMGetPointerToGlobal(
				instance, v.getInstance());
	}

	protected void finalize() {
		org.jllvm.bindings.ExecutionEngine.LLVMDisposeExecutionEngine(instance);
	}
}
