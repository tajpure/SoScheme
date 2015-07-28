package org.jllvm.value.user.constant;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueUnsigned_int;

public abstract class ConstantAggregate extends Constant {
	public ConstantExpression extractValue(long[] indices) {
		LLVMOpaqueUnsigned_int params = Core.new_UnsignedIntArray(indices.length);
		for(int i=0;i<indices.length;i++)
			Core.UnsignedIntArray_setitem(params,i,indices[i]);
		ConstantExpression result = new ConstantExpression(Core.LLVMConstExtractValue(instance,params,indices.length));
		Core.delete_UnsignedIntArray(params);
		return result;
	}
	
	public ConstantExpression insertValue(Constant value,long[] indices) {
		LLVMOpaqueUnsigned_int params = Core.new_UnsignedIntArray(indices.length);
		for(int i=0;i<indices.length;i++)
			Core.UnsignedIntArray_setitem(params,i,indices[i]);
		ConstantExpression result = new ConstantExpression(Core.LLVMConstInsertValue(instance,value.instance,params,indices.length));
		Core.delete_UnsignedIntArray(params);
		return result;
	}
}
