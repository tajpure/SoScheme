package org.jllvm._type;

import org.jllvm.Context;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;
import org.jllvm.bindings.LLVMOpaqueTypeRefArray;

/* Implements all operations on struct types from Core.h */
public class StructType extends Type {
	protected StructType() {
		instance = null;
	}
	public StructType(Type[] elements,boolean packed) {
		LLVMOpaqueTypeRefArray elmnts = Core.new_LLVMTypeRefArray(elements.length);
		for(int i=0;i<elements.length;i++)
			Core.LLVMTypeRefArray_setitem(elmnts,i,elements[i].getInstance());
		LLVMOpaqueType tr = Core.LLVMStructType(elmnts,elements.length,packed ? 0 : 1);
		Core.delete_LLVMTypeRefArray(elmnts);
		instance = tr;
		llvm_types.put(instance,this);
	}
	
	public StructType(Context context, Type[] elements,boolean packed) {
		LLVMOpaqueTypeRefArray elmnts = Core.new_LLVMTypeRefArray(elements.length);
		for(int i=0;i<elements.length;i++)
			Core.LLVMTypeRefArray_setitem(elmnts,i,elements[i].getInstance());
		LLVMOpaqueType tr = Core.LLVMStructTypeInContext(context.getInstance(),elmnts,elements.length,packed ? 0 : 1);
		Core.delete_LLVMTypeRefArray(elmnts);
		instance = tr;
		llvm_types.put(instance,this);
	}
	
	public StructType(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMStructTypeKind);
	}
	
	public long countElementTypes() {
		return Core.LLVMCountStructElementTypes(instance);
	}
	
	public Type[] getElementTypes() {
		int num_elements = (int)countElementTypes();
		LLVMOpaqueTypeRefArray elements = Core.new_LLVMTypeRefArray(num_elements);
		Core.LLVMGetStructElementTypes(instance,elements);
		Type[] result = new Type[num_elements];
		for(int i=0;i<result.length;i++)
			result[i] = Type.getType(Core.LLVMTypeRefArray_getitem(elements,i));
		Core.delete_LLVMTypeRefArray(elements);
		return result;
	}
	
	public boolean isPacked() {
		return Core.LLVMIsPackedStruct(instance) > 0;
	}
}
