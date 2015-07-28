package org.jllvm.value.user;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.Value;

/* Core.h doesn't specify anything about the type User, so there's pretty much nothing here. */
public class User extends Value {
	protected User() {
		instance = null;
	}
	
	public User(LLVMOpaqueValue val) {
		super(Core.LLVMIsAUser(val));
	}
}
