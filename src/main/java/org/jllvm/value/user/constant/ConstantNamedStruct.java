package org.jllvm.value.user.constant;

import org.jllvm._type.StructType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;

public class ConstantNamedStruct extends ConstantAggregate {
	public ConstantNamedStruct(StructType structType, Constant[] elements,boolean packed) {
		LLVMOpaqueValueRefArray params = Core.new_LLVMValueRefArray(elements.length);
		for(int i=0;i<elements.length;i++)
			Core.LLVMValueRefArray_setitem(params,i,elements[i].instance);
		LLVMOpaqueValue struct = Core.LLVMConstNamedStruct(structType.getInstance(), params, elements.length);
		Core.delete_LLVMValueRefArray(params);
		assert(Core.LLVMIsConstant(struct) != 0);
		instance = struct;
	}
}
