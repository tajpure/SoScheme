package org.jllvm;

import java.math.BigInteger;

import org.jllvm._type.IntegerType;
import org.jllvm._type.StructType;
import org.jllvm._type.Type;
import org.jllvm.bindings.LLVMByteOrdering;
import org.jllvm.bindings.LLVMOpaqueTargetData;
import org.jllvm.bindings.Target;
import org.jllvm.value.user.constant.GlobalValue;

public class TargetData {
	protected LLVMOpaqueTargetData instance;
	
	private boolean created = true;
	
	public LLVMOpaqueTargetData getInstance() {
		return instance;
	}
	
	public static void initializeAllTargetInfos() {
		Target.LLVMInitializeAllTargetInfos();
	}
	
	public static void initializeAllTargets() {
		Target.LLVMInitializeAllTargets();
	}

	public static boolean initializeNativeTarget() {
		return Target.LLVMInitializeNativeTarget() != 0;
	}
	
	public String stringRepresentation() {
		return Target.LLVMCopyStringRepOfTargetData(instance);
	}
	
	public LLVMByteOrdering getByteOrdering() {
		return Target.LLVMByteOrder(instance);
	}
	
	public long getPointerSize() {
		return Target.LLVMPointerSize(instance);
	}
	
	public IntegerType intPtrType() {
		return new IntegerType(Target.LLVMIntPtrType(instance));
	}
	
	public long sizeOfTypeInBits(Type t) {
		return Target.LLVMSizeOfTypeInBits(instance,t.getInstance()).longValue();
	}
	
	public long storeSizeOfType(Type t) {
		return Target.LLVMStoreSizeOfType(instance,t.getInstance()).longValue();
	}
	
	public long abiSizeOfType(Type t) {
		return Target.LLVMABISizeOfType(instance,t.getInstance()).longValue();
	}
	
	public long abiAlignmentOfType(Type t) {
		return Target.LLVMABIAlignmentOfType(instance,t.getInstance());
	}
	
	public long callFrameAlignmentOfType(Type t) {
		return Target.LLVMCallFrameAlignmentOfType(instance,t.getInstance());
	}
	
	public long preferredAlignmentOfType(Type t) {
		return Target.LLVMPreferredAlignmentOfType(instance,t.getInstance());
	}
	
	public long preferredAlignmentOfGlobal(GlobalValue global) {
		return Target.LLVMPreferredAlignmentOfGlobal(instance,global.getInstance());
	}
	
	public long elementAtOffset(StructType struct,long offset) {
		return Target.LLVMElementAtOffset(instance,struct.getInstance(),BigInteger.valueOf(offset));
	}
	
	public long offsetOfElement(StructType struct,int element) {
		return Target.LLVMOffsetOfElement(instance,struct.getInstance(),element).longValue();
	}
	
	public TargetData(LLVMOpaqueTargetData td) {
		assert(td != null);
		instance = td;
		created = false;
	}
	
	public TargetData(String stringRep) {
		instance = Target.LLVMCreateTargetData(stringRep);
		assert(instance != null);
	}
	
	protected void finalize() {
		if (created)
			Target.LLVMDisposeTargetData(instance);
	} 
}
