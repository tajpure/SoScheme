package org.jllvm;

import java.util.HashMap;

import org.jllvm._type.Type;
import org.jllvm.bindings.BitWriter;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueModule;
import org.jllvm.bindings.LLVMOpaqueStringRefArray;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.constant.GlobalVariable;

/* Fully translated all functions for Modules from Core.java/Core.h back to a Module class. 
   Also fully translated all functions for Modules from BitWriter.java/Bitwriter.h to this Module class. */
public class Module {
	protected static HashMap<LLVMOpaqueModule,Module> llvm_modules;
	protected LLVMOpaqueModule instance;
	protected String identifier;
	
	public LLVMOpaqueModule getInstance() {
		return instance;
	}
	
	public String getModuleIdentifier() {
		return identifier;
	}
	
	public String getDataLayout() {
		return Core.LLVMGetDataLayout(instance);
	}
	
	public void setDataLayout(String triple) {
		Core.LLVMSetDataLayout(instance,triple);
	}
	
	public String getTargetTriple() {
		return Core.LLVMGetTarget(instance);
	} 
	
	public void setTargetTriple(String triple) {
		Core.LLVMSetTarget(instance,triple);
	}
	
	public void dump() {
		Core.LLVMDumpModule(instance);
	}
	
	public void printToFile(String fileName) {
		LLVMOpaqueStringRefArray outerrs = org.jllvm.bindings.ExecutionEngine.new_StringArray(1);
		int ret = Core.LLVMPrintModuleToFile(instance, fileName, outerrs);
		String outerr = org.jllvm.bindings.ExecutionEngine.StringArray_getitem(outerrs, 0);
		org.jllvm.bindings.ExecutionEngine.delete_StringArray(outerrs);
		if(ret != 0) {
			throw new RuntimeException(outerr);
		}
	}
	
	public GlobalVariable addGlobal(Type type,String name) {
		return new GlobalVariable(Core.LLVMAddGlobal(instance,type.getInstance(),name));
	}
	
	public GlobalVariable getNamedGlobal(String name) {
		return GlobalVariable.getGlobalVariable(Core.LLVMGetNamedGlobal(instance,name));
	}
	
	public GlobalVariable getFirstGlobal() {
		return GlobalVariable.getGlobalVariable(Core.LLVMGetFirstGlobal(instance));
	}
	
	public GlobalVariable getLastGlobal() {
		return GlobalVariable.getGlobalVariable(Core.LLVMGetLastGlobal(instance));
	}
	
	public Function getNamedFunction(String name) {
		return Function.getFunction(Core.LLVMGetNamedFunction(instance,name));
	}
	
	public Function getFirstFunction() {
		return Function.getFunction(Core.LLVMGetFirstFunction(instance));
	}
	
	public Function getLastFunction() {
		return Function.getFunction(Core.LLVMGetLastFunction(instance));
	}
	
	public Context getContext() {
		return Context.getContext(Core.LLVMGetModuleContext(instance));
	}
	
	public boolean writeBitcodeToFile(String path) {
		int result = BitWriter.LLVMWriteBitcodeToFile(instance,path);
		return (result == 0);
	}
	
	public boolean writeBitcodeToFD(int fd,boolean shouldClose,boolean unbuffered) {
		int result = BitWriter.LLVMWriteBitcodeToFD(instance,fd,shouldClose ? 1 : 0,unbuffered ? 1 : 0);
		return (result == 0);
	}
	
	public boolean writeBitcodeToFileHandle(int handle) {
		int result = BitWriter.LLVMWriteBitcodeToFileHandle(instance,handle);
		return (result == 0);
	}
	
	public Module(String moduleid) {
		instance = Core.LLVMModuleCreateWithName(moduleid);
		identifier = moduleid;
		if(llvm_modules == null)
			llvm_modules = new HashMap<LLVMOpaqueModule,Module>();
		llvm_modules.put(instance,this);
	}
	
	public Module(String name,Context context) {
		instance = Core.LLVMModuleCreateWithNameInContext(name,context.getInstance());
		identifier = name;
		if(llvm_modules == null)
			llvm_modules = new HashMap<LLVMOpaqueModule,Module>();
		llvm_modules.put(instance,this);
	}
	
	public static Module getModule(LLVMOpaqueModule m) {
		if(llvm_modules == null)
			llvm_modules = new HashMap<LLVMOpaqueModule,Module>();
		Module result = llvm_modules.get(m);
		assert(result != null);
		return result;
	}
	
	protected void finalize() {
		Core.LLVMDisposeModule(instance);
	}
}
