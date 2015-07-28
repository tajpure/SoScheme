package org.jllvm.value;

import java.util.HashMap;

import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;

/* Implements all methods from Core.h dealing with the base class Value. */
public class Value {
	protected static HashMap<LLVMOpaqueValue, Value> llvm_values;
	public LLVMOpaqueValue instance;

	protected Value() {
		instance = null;
		if (llvm_values == null)
			llvm_values = new HashMap<LLVMOpaqueValue, Value>();
	}

	public Value(LLVMOpaqueValue val) {
		assert (val != null);
		instance = val;
		if (llvm_values == null)
			llvm_values = new HashMap<LLVMOpaqueValue, Value>();
		llvm_values.put(instance, this);
	}

	public Type typeOf() {
		return Type.getType(Core.LLVMTypeOf(instance));
	}

	public String getValueName() {
		return Core.LLVMGetValueName(instance);
	}

	public void setValueName(String name) {
		Core.LLVMSetValueName(instance, name);
	}

	public void dump() {
		Core.LLVMDumpValue(instance);
	}

	public LLVMOpaqueValue getInstance() {
		return instance;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Value)
			return ((Value) obj).instance == instance;
		else
			return false;
	}

	public static Value getValue(LLVMOpaqueValue val) {
		if (llvm_values == null)
			llvm_values = new HashMap<LLVMOpaqueValue, Value>();
		Value result = llvm_values.get(val);
		return result;
	}

	protected void finalize() {
		llvm_values.remove(instance);
	}
}
