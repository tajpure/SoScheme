package org.jllvm.value.user.constant;

import org.jllvm._type.PointerType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;

public class ConstantPointer extends Constant {
	public ConstantPointer(LLVMOpaqueValue c) {
		super(c);
		assert(typeOf() instanceof PointerType);
	}
	
	public ConstantExpression toInteger(PointerType targetType) {
		return new ConstantExpression(Core.LLVMConstPtrToInt(instance,targetType.getInstance()));
	}
}
