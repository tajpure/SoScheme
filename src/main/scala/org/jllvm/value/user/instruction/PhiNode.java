package org.jllvm.value.user.instruction;

import org.jllvm.InstructionBuilder;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueBasicBlockRefArray;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;

public class PhiNode extends Instruction {
	public PhiNode(InstructionBuilder builder,Type type,String name) {
		instance = Core.LLVMBuildPhi(builder.getInstance(),type.getInstance(),name);
	}
	
	public void addIncoming(Value[] values,BasicBlock[] blocks) {
		assert(values.length == blocks.length);
		LLVMOpaqueBasicBlockRefArray blockArray = Core.new_LLVMBasicBlockRefArray(values.length);
		for(int i=0;i<values.length;i++)
			Core.LLVMBasicBlockRefArray_setitem(blockArray,i,blocks[i].getBBInstance());
		LLVMOpaqueValueRefArray valueArray = Core.new_LLVMValueRefArray(values.length);
		for(int i=0;i<values.length;i++)
			Core.LLVMValueRefArray_setitem(valueArray,i,values[i].getInstance());
		Core.LLVMAddIncoming(instance,valueArray,blockArray,values.length);
		Core.delete_LLVMValueRefArray(valueArray);
		Core.delete_LLVMBasicBlockRefArray(blockArray);
	}
	
	public long countIncoming() {
		return Core.LLVMCountIncoming(instance);
	}
	
	public Value getIncomingValue(long index) {
		assert(index >= 0);
		return Value.getValue(Core.LLVMGetIncomingValue(instance,index));
	}
	
	public BasicBlock getIncomingBlock(long index) {
		assert(index >= 0);
		return BasicBlock.getBasicBlock(Core.LLVMGetIncomingBlock(instance,index));
	}
}
