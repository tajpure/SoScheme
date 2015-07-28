package org.jllvm.value.user.constant;

import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;

public class ConstantArray extends ConstantAggregate {
	public ConstantArray(Type elementType,Constant[] elements) {
		LLVMOpaqueValueRefArray params = Core.new_LLVMValueRefArray(elements.length);
		for(int i=0;i<elements.length;i++)
			Core.LLVMValueRefArray_setitem(params,i,elements[i].instance);
		LLVMOpaqueValue array = Core.LLVMConstArray(elementType.getInstance(),params,elements.length);
		Core.delete_LLVMValueRefArray(params);
		assert(Core.LLVMIsConstant(array) != 0);
		instance = array;
	}
}
