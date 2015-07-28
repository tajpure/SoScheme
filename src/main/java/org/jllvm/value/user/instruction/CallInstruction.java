package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.FunctionType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMAttribute;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;
import org.jllvm.value.Value;

public class CallInstruction extends Instruction {
	public long getCallingConvention() {
		return Core.LLVMGetInstructionCallConv(instance);
	}
	
	public void setCallingConvention(long CC) {
		Core.LLVMSetInstructionCallConv(instance,CC);
	}

	public void addAttribute(long index,LLVMAttribute attr) {
		assert(index >= 0);
		Core.LLVMAddInstrAttribute(instance,index,attr);
	}
	
	public void removeAttribute(long index,LLVMAttribute attr) {
		assert(index >= 0);
		Core.LLVMRemoveInstrAttribute(instance,index,attr);
	}
	
	public void setParameterAlignment(long index,long alignment) {
		assert(index >= 0 && alignment >= 0);
		Core.LLVMSetInstrParamAlignment(instance,index,alignment);
	}
	
	public boolean isTailCall() {
		return Core.LLVMIsTailCall(instance) > 0;
	}
	
	public void setTailCall(boolean tailCall) {
		Core.LLVMSetTailCall(instance,tailCall ? 1 : 0);
	}
	
	public CallInstruction(InstructionBuilder builder,Value func,Value[] arguments,String name) {
		assert(func.typeOf() instanceof FunctionType);
		assert(arguments.length == ((FunctionType)func.typeOf()).countParamTypes());
		LLVMOpaqueValueRefArray args = Core.new_LLVMValueRefArray(arguments.length);
		for(int i=0;i<arguments.length;i++)
			Core.LLVMValueRefArray_setitem(args,i,arguments[i].getInstance());
		instance = Core.LLVMBuildCall(builder.getInstance(),func.getInstance(),args,arguments.length,name);
		Core.delete_LLVMValueRefArray(args);
	}
}
