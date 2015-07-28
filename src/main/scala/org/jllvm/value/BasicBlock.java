package org.jllvm.value;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueBasicBlock;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.instruction.Instruction;

public class BasicBlock extends Value {
	public BasicBlock(LLVMOpaqueValue val) {
		assert(Core.LLVMValueIsBasicBlock(val) != 0);
		instance = val;
		llvm_values.put(instance,this);
	}
	
	public BasicBlock(LLVMOpaqueBasicBlock bb) {
		instance = Core.LLVMBasicBlockAsValue(bb);
		assert(Core.LLVMValueIsBasicBlock(instance) != 0);
		llvm_values.put(instance,this);
	}
	
	public LLVMOpaqueBasicBlock getBBInstance() {
		return Core.LLVMValueAsBasicBlock(instance);
	}
	
	public Function getParent() {
		LLVMOpaqueBasicBlock bb = getBBInstance();
		return Function.getFunction(Core.LLVMGetBasicBlockParent(bb));
	}
	
	public BasicBlock getNextBasicBlock() {
		return getBasicBlock(Core.LLVMGetNextBasicBlock(getBBInstance()));
	}
	
	public BasicBlock getPreviousBasicBlock() {
		return getBasicBlock(Core.LLVMGetPreviousBasicBlock(getBBInstance()));
	}
	
	public BasicBlock insertBasicBlockBefore(String name) {
		return new BasicBlock(Core.LLVMInsertBasicBlock(getBBInstance(),name));
	}
	
	public Instruction getFirstInstruction() {
		return new Instruction(Core.LLVMGetFirstInstruction(getBBInstance()));
	}
	
	public Instruction getLastInstruction() {
		return new Instruction(Core.LLVMGetLastInstruction(getBBInstance()));
	}

	public static BasicBlock getBasicBlock(LLVMOpaqueValue val) {
		assert(val != null);
		Value possibility = Value.getValue(val);
		if(possibility == null)
			return new BasicBlock(val);
		else
			return (BasicBlock)possibility;
	}

	public static BasicBlock getBasicBlock(LLVMOpaqueBasicBlock bb) {
		assert(bb != null);
		return getBasicBlock(Core.LLVMBasicBlockAsValue(bb));
	}
	
	protected void finalize() {
		super.finalize();
		Core.LLVMDeleteBasicBlock(getBBInstance());
	}
}
