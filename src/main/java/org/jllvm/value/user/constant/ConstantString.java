package org.jllvm.value.user.constant;

import org.jllvm.bindings.Core;

public class ConstantString extends Constant {
	public ConstantString(String str,boolean nullTerminate) {
		super(Core.LLVMConstString(str,str.length(),nullTerminate ? 0 : 1));
	}
}
