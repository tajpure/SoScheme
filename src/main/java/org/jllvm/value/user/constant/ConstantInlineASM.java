package org.jllvm.value.user.constant;

import org.jllvm._type.Type;
import org.jllvm.bindings.Core;

public class ConstantInlineASM extends ConstantExpression {
	public ConstantInlineASM(Type type,String asm,String constraints,boolean hasSideEffects,boolean isAlignStack) {
		super(Core.LLVMConstInlineAsm(type.getInstance(),asm,constraints,hasSideEffects ? 1 : 0,isAlignStack ? 1 : 0));
	}
}
