package org.jllvm;

import org.jllvm.bindings.LLVMOpaqueExecutionEngineRefArray;
import org.jllvm.bindings.LLVMOpaqueStringRefArray;

public class Interpreter extends ExecutionEngine {
	public Interpreter(Module mod) throws Exception {
		super(null);
		LLVMOpaqueExecutionEngineRefArray engines = org.jllvm.bindings.ExecutionEngine.new_LLVMExecutionEngineRefArray(1);
		LLVMOpaqueStringRefArray outerrs = org.jllvm.bindings.ExecutionEngine.new_StringArray(1);
		boolean success = org.jllvm.bindings.ExecutionEngine.LLVMCreateInterpreterForModule(engines,mod.getInstance(),outerrs) > 0;
		String outerr = org.jllvm.bindings.ExecutionEngine.StringArray_getitem(outerrs,0);
		org.jllvm.bindings.ExecutionEngine.delete_StringArray(outerrs); outerrs = null;
		instance = org.jllvm.bindings.ExecutionEngine.LLVMExecutionEngineRefArray_getitem(engines,0);
		org.jllvm.bindings.ExecutionEngine.delete_LLVMExecutionEngineRefArray(engines); engines = null;
		if(!success)
			throw new Exception(outerr);
	}
}
