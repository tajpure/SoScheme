package org.jllvm.value.user.constant;

import org.jllvm._type.IntegerType;
import org.jllvm._type.RealType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;

public class ConstantReal extends Constant {
	public ConstantReal(Type realType,double N) {
		super(Core.LLVMConstReal(realType.getInstance(),N));
	}
	
	public ConstantReal(Type realType,String text) {
		super(Core.LLVMConstRealOfString(realType.getInstance(),text));
	}
	
	public ConstantExpression truncate(RealType targetType) {
		return new ConstantExpression(Core.LLVMConstFPTrunc(instance,targetType.getInstance()));
	}
	
	public ConstantExpression extend(RealType targetType) {
		return new ConstantExpression(Core.LLVMConstFPExt(instance,targetType.getInstance()));
	}
	
	public ConstantExpression realToInteger(IntegerType targetType,boolean signed) {
		if(signed)
			return new ConstantExpression(Core.LLVMConstFPToSI(instance,targetType.getInstance()));
		else
			return new ConstantExpression(Core.LLVMConstFPToUI(instance,targetType.getInstance()));
	}
}
