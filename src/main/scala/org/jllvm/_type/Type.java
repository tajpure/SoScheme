package org.jllvm._type;

import java.util.HashMap;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;

/* Implements all methods from Core.h dealing with the base class Type. */
public class Type {
	protected static HashMap<LLVMOpaqueType,Type> llvm_types;
	protected LLVMOpaqueType instance;
	
	public LLVMTypeKind getTypeKind() {
		return Core.LLVMGetTypeKind(instance);
	}
	
	public LLVMOpaqueType getInstance() {
		return instance;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Type)
			return ((Type)obj).instance == instance;
		else
			return false;
	}
	
	protected Type() {
		instance = null;
		if(llvm_types == null)
			llvm_types = new HashMap<LLVMOpaqueType,Type>();
	}
	
	public Type(LLVMOpaqueType tr) {
		instance = tr;
		if(llvm_types == null)
			llvm_types = new HashMap<LLVMOpaqueType,Type>();
		llvm_types.put(instance,this);
	}
	
	public static Type getType(LLVMOpaqueType tr) {
		if(llvm_types == null)
			llvm_types = new HashMap<LLVMOpaqueType,Type>();
		Type result = llvm_types.get(tr);
		if(result == null) {
			LLVMTypeKind kind = Core.LLVMGetTypeKind(tr);
			if(kind == LLVMTypeKind.LLVMVoidTypeKind)
				result = new VoidType(tr);
			else if(kind == LLVMTypeKind.LLVMFloatTypeKind)
				result = new FloatType();
			else if(kind == LLVMTypeKind.LLVMDoubleTypeKind)
				result = new DoubleType();
			else if(kind == LLVMTypeKind.LLVMX86_FP80TypeKind)
				result = new X86FP80Type(tr);
			else if(kind == LLVMTypeKind.LLVMFP128TypeKind)
				result = new FP128Type();
			else if(kind == LLVMTypeKind.LLVMPPC_FP128TypeKind)
				result = new PPCFP128Type(tr);
			else if(kind == LLVMTypeKind.LLVMLabelTypeKind)
				result = new LabelType();
			else if(kind == LLVMTypeKind.LLVMIntegerTypeKind)
				result = new IntegerType(tr);
			else if(kind == LLVMTypeKind.LLVMFunctionTypeKind)
				result = new FunctionType(tr);
			else if(kind == LLVMTypeKind.LLVMStructTypeKind)
				result = new StructType(tr);
			else if(kind == LLVMTypeKind.LLVMArrayTypeKind)
				result = new ArrayType(tr);
			else if(kind == LLVMTypeKind.LLVMPointerTypeKind)
				result = new PointerType(tr);
			else if(kind == LLVMTypeKind.LLVMVectorTypeKind)
				result = new VectorType(tr);
			/*else if(kind == LLVMMetadataTypeKind)
				result = new LLVMMetadataType();*/
		}
		assert(result != null);
		return result;
	}
}
