package org.jllvm.value.user.constant;

import org.jllvm._type.VectorType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;

public class ConstantVector extends Constant {
	public ConstantVector(Constant[] elements) {
		LLVMOpaqueValueRefArray params = Core.new_LLVMValueRefArray(elements.length);
		for(int i=0;i<elements.length;i++)
			Core.LLVMValueRefArray_setitem(params,i,elements[i].instance);
		instance = Core.LLVMConstVector(params,elements.length);
		Core.delete_LLVMValueRefArray(params);
		assert(Core.LLVMIsConstant(instance) != 0);
	}
	
	public ConstantExpression extractElement(ConstantInteger index) {
		return new ConstantExpression(Core.LLVMConstExtractElement(instance,index.instance));
	}
	
	public ConstantExpression insertElement(Constant element,ConstantInteger index) {
		assert(element.typeOf().equals(((VectorType)typeOf()).getElementType()));
		return new ConstantExpression(Core.LLVMConstInsertElement(instance,element.instance,index.instance));
	}
}
