package org.jllvm._type;

import org.jllvm.bindings.Core;

public class LabelType extends Type {
	public LabelType() {
		super(Core.LLVMLabelType());
	}
}
