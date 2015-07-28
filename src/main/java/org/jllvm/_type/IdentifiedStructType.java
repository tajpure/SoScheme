package org.jllvm._type;

import org.jllvm.Context;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMTypeKind;
import org.jllvm.bindings.LLVMOpaqueType;
import org.jllvm.bindings.LLVMOpaqueTypeRefArray;

/* Implements operations on identified struct types from Core.h */
public class IdentifiedStructType extends StructType {
	//Creates an identified struct, which can later become recursive
	public IdentifiedStructType(Context context, String name) {
		instance = Core.LLVMStructCreateNamed(context.getInstance(),name);
	}
	
	public IdentifiedStructType(Context context) {
		instance = Core.LLVMStructCreateNamed(context.getInstance(),"");
	}
	
	public IdentifiedStructType(String name) {
		instance = Core.LLVMStructCreateNamed(Context.getGlobalContext().getInstance(),name);
	}
	
	public IdentifiedStructType(LLVMOpaqueType t) {
		super(t);
		assert(Core.LLVMGetTypeKind(t) == LLVMTypeKind.LLVMStructTypeKind);
	}
	
	public String getName() {
		return Core.LLVMGetStructName(instance);
	}
	
	public void setBody(Type[] elementTypes,boolean packed) {
		LLVMOpaqueTypeRefArray elements = Core.new_LLVMTypeRefArray(elementTypes.length);
		for(int i=0;i<elementTypes.length;i++)
			Core.LLVMTypeRefArray_setitem(elements,i,elementTypes[i].getInstance());
		Core.LLVMStructSetBody(instance,elements,elementTypes.length,packed ? 1 : 0);
		Core.delete_LLVMTypeRefArray(elements);
	}
	
	public boolean isOpaque() {
		return (Core.LLVMIsOpaqueStruct(instance) > 0);
	}
}
