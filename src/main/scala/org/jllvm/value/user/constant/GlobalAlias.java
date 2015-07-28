package org.jllvm.value.user.constant;

import org.jllvm.Module;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;

public class GlobalAlias extends GlobalValue {
	public GlobalAlias(Module parent,Type type,Constant aliasee,String name) {
		super(Core.LLVMAddAlias(parent != null ? parent.getInstance() : null,type.getInstance(),aliasee.getInstance(),name));
	}
}
