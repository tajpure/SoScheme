package org.jllvm._type;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

/* Implements all methods from Core.h for integer types. */
public class IntegerType extends Type {
	public static IntegerType i64 = new IntegerType(Core.LLVMInt64Type());
	public static IntegerType i32 = new IntegerType(Core.LLVMInt32Type());
	public static IntegerType i16 = new IntegerType(Core.LLVMInt16Type());
	public static IntegerType i8 = new IntegerType(Core.LLVMInt8Type());
	public static IntegerType i1 = new IntegerType(Core.LLVMInt1Type());

	public long getWidth() {
		return Core.LLVMGetIntTypeWidth(instance);
	}
	
	public IntegerType(LLVMOpaqueType tr) {
		super(tr);
		assert(getTypeKind() == LLVMTypeKind.LLVMIntegerTypeKind);
	}
	
	public IntegerType(long NumBits) {
		super(Core.LLVMIntType(NumBits));
	}

	public String toString() {
		return "i" + getWidth();
	}
}
