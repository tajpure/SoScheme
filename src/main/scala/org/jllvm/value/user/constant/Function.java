package org.jllvm.value.user.constant;

import org.jllvm.Module;
import org.jllvm._type.FunctionType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMCallConv;
import org.jllvm.bindings.LLVMOpaqueValue;
import org.jllvm.bindings.LLVMOpaqueBasicBlockRefArray;
import org.jllvm.bindings.LLVMOpaqueValueRefArray;
import org.jllvm.value.Argument;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;

public class Function extends GlobalValue {
	public Function(Module mod,String name,FunctionType funcType) {
		super(Core.LLVMAddFunction(mod != null ? mod.getInstance() : null,name,funcType.getInstance()));
		llvm_values.put(instance,this);
	}
	
	public Function(LLVMOpaqueValue val) {
		super(val);
		assert(Core.LLVMIsAFunction(val) != null);
	}
	
	public Function getNextFunction() {
		return getFunction(Core.LLVMGetNextFunction(instance));
	}
	
	public Function getPreviousFunction() {
		return getFunction(Core.LLVMGetPreviousFunction(instance));
	}
	
	public long getIntrinsicID() {
		return Core.LLVMGetIntrinsicID(instance);
	}
	
	public long getCallingConvention() {
		return Core.LLVMGetFunctionCallConv(instance);
	}
	
	public void setCallingConvention(long callconv) {
		assert(callconv >= 0);
		Core.LLVMSetFunctionCallConv(instance,callconv);
	}
	
	public void setCallingConvention(LLVMCallConv callconv) {
		Core.LLVMSetFunctionCallConv(instance,callconv.swigValue());
	}
	
	public String getGC() {
		return Core.LLVMGetGC(instance);
	}
	
	public void setGC(String name) {
		Core.LLVMSetGC(instance,name);
	}
	
	public long countParameters() {
		return Core.LLVMCountParams(instance);
	}
	
	public Argument[] getParameters() {
		int num_parameters = (int)countParameters();
		LLVMOpaqueValueRefArray params = Core.new_LLVMValueRefArray(num_parameters);
		Core.LLVMGetParams(instance,params);
		Argument[] result = new Argument[num_parameters];
		for(int i=0;i<num_parameters;i++)
			result[i] = new Argument(Core.LLVMValueRefArray_getitem(params,i));
		Core.delete_LLVMValueRefArray(params);
		return result;
	}
	
	public Argument getParameter(int i) {
		return new Argument(Core.LLVMGetParam(instance,i));
	}
	
	public Argument getFirstParameter() {
		return new Argument(Core.LLVMGetFirstParam(instance));
	}
	
	public Argument getLastParameter() {
		return new Argument(Core.LLVMGetLastParam(instance));
	}
	
	public long countBasicBlocks() {
		return Core.LLVMCountBasicBlocks(instance);
	}
	
	public BasicBlock[] getBasicBlocks() {
		int num_blocks = (int)countBasicBlocks();
		BasicBlock[] blocks = new BasicBlock[num_blocks];
		LLVMOpaqueBasicBlockRefArray bbs = Core.new_LLVMBasicBlockRefArray(num_blocks);
		Core.LLVMGetBasicBlocks(instance,bbs);
		for(int i=0;i<num_blocks;i++)
			blocks[i] = BasicBlock.getBasicBlock(Core.LLVMBasicBlockRefArray_getitem(bbs,i));
		Core.delete_LLVMBasicBlockRefArray(bbs);
		return blocks;
	}
	
	public BasicBlock getFirstBasicBlock() {
		return BasicBlock.getBasicBlock(Core.LLVMGetFirstBasicBlock(instance));
	}
	
	public BasicBlock getLastBasicBlock() {
		return BasicBlock.getBasicBlock(Core.LLVMGetLastBasicBlock(instance));
	}
	
	public BasicBlock getEntryBasicBlock() {
		return BasicBlock.getBasicBlock(Core.LLVMGetEntryBasicBlock(instance));
	}
	
	public BasicBlock appendBasicBlock(String name) {
		return new BasicBlock(Core.LLVMAppendBasicBlock(instance,name));
	}
	
	public BasicBlock appendBasicBlock() {
		return new BasicBlock(Core.LLVMAppendBasicBlock(instance, ""));
	}
	
	public static Function getFunction(LLVMOpaqueValue f) {
		Value possibility = Value.getValue(f);
		if(possibility == null)
			return null;
		else
			return (Function)possibility;
	}
	
	public void eraseFromParent() {
		this.finalize();
	}
	
	protected void finalize() {
		Core.LLVMDeleteFunction(instance);
		super.finalize();
	}
}
