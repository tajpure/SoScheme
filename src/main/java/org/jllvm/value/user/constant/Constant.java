package org.jllvm.value.user.constant;

import org.jllvm._type.PointerType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;
import org.jllvm.value.user.User;

/* Implements all the methods from Core.h for constants. */
public class Constant extends User {
	public boolean isNullValue() {
		return Core.LLVMIsNull(instance) != 0;
	}
	
	public static Constant allOnes(Type type) {
		return new Constant(Core.LLVMConstAllOnes(type.getInstance()));
	}
	
	public static Constant constNull(Type type) {
		LLVMOpaqueValue val = Core.LLVMConstNull(type.getInstance());
		if(type instanceof PointerType)
			return new ConstantPointer(val);
		else
			return new Constant(val);
	}
	
	public static Constant getUndef(Type type) {
		return new Constant(Core.LLVMGetUndef(type.getInstance()));
	}
	
	public boolean isUndef() {
		return Core.LLVMIsUndef(instance) != 0;
	}
	
	public ConstantExpression negative() {
		return new ConstantExpression(Core.LLVMConstNeg(instance));
	}
	
	public ConstantExpression not() {
		return new ConstantExpression(Core.LLVMConstNot(instance));
	}
	
	public ConstantExpression getElementPointer(Constant[] indices) {
		LLVMOpaqueValueRefArray params = Core.new_LLVMValueRefArray(indices.length);
		for(int i=0;i<indices.length;i++)
			Core.LLVMValueRefArray_setitem(params,i,indices[i].instance);
		ConstantExpression result = new ConstantExpression(Core.LLVMConstGEP(instance,params,indices.length));
		Core.delete_LLVMValueRefArray(params);
		return result;
	}
	
	public ConstantExpression bitCast(Type targetType) {
		return new ConstantExpression(Core.LLVMConstBitCast(instance,targetType.getInstance()));
	}
	
	protected Constant() {
		instance = null;
	}
	
	public Constant(LLVMOpaqueValue val) {
		super(val);
		assert(Core.LLVMIsConstant(val) != 0);
	}
}
