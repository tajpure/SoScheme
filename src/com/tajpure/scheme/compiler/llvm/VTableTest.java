package com.tajpure.scheme.compiler.llvm;

import org.jllvm.ExecutionEngine;
import org.jllvm.InstructionBuilder;
import org.jllvm.Module;
import org.jllvm.NativeLibrary;
import org.jllvm._type.FunctionType;
import org.jllvm._type.IdentifiedStructType;
import org.jllvm._type.IntegerType;
import org.jllvm._type.PointerType;
import org.jllvm._type.Type;
import org.jllvm._type.VoidType;
import org.jllvm.bindings.LLVMLinkage;
import org.jllvm.generic.GenericValue;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;
import org.jllvm.value.user.constant.Constant;
import org.jllvm.value.user.constant.ConstantInteger;
import org.jllvm.value.user.constant.ConstantNamedStruct;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.constant.GlobalVariable;
import org.jllvm.value.user.instruction.CallInstruction;
import org.jllvm.value.user.instruction.GetElementPointerInstruction;
import org.jllvm.value.user.instruction.LoadInstruction;
import org.jllvm.value.user.instruction.MultiplyInstruction;
import org.jllvm.value.user.instruction.StackAllocation;

public class VTableTest {
	
	public static void main(String[] args) throws Exception {
		NativeLibrary.load();
		
		InstructionBuilder builder = new InstructionBuilder();
		Module module = new Module("com.hoolai.compiler.VTest");
		
		//Structs
		IdentifiedStructType fooVtableType = new IdentifiedStructType("FooVtableType");
		PointerType fooVtablePtr = new PointerType(fooVtableType, 0);
		
		IdentifiedStructType foo = new IdentifiedStructType("Foo");
		foo.setBody(new Type[]{fooVtablePtr, IntegerType.i32}, false);
		
		//GetLengthTimesTwo
		FunctionType fooGetLengthTimesTwoType = new FunctionType(IntegerType.i32, new Type[]{new PointerType(foo, 0)}, false);
		Function fooGetLengthTimesTwo = new Function(module, "FooGetLengthTimesTwo", fooGetLengthTimesTwoType);
		fooGetLengthTimesTwo.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		
		BasicBlock fooGetLengthTimesTwoBody = fooGetLengthTimesTwo.appendBasicBlock("fooGetLengthTimesTwoBody");
		builder.positionBuilderAtEnd(fooGetLengthTimesTwoBody);
		GetElementPointerInstruction one = builder.buildGEP(fooGetLengthTimesTwo.getParameter(0), 1, "1");
		LoadInstruction two = builder.buildLoad(one, "2");
		MultiplyInstruction three = builder.buildMul(two, ConstantInteger.constI32(2), "3");
		builder.buildRet(three);
		
		//InitGobal
		fooVtableType.setBody(new Type[]{fooGetLengthTimesTwo.typeOf()}, false);
		GlobalVariable fooVtableData = module.addGlobal(fooVtableType, "fooVtableData");
		ConstantNamedStruct cs = new ConstantNamedStruct(fooVtableType, new Constant[]{fooGetLengthTimesTwo}, false);
		fooVtableData.setInitializer(cs);
		
		//CreateDefault
		FunctionType fooCreateDefaultType = new FunctionType(new VoidType(), new Type[]{new PointerType(foo, 0)}, false);
		Function fooCreateDefault = new Function(module, "FooCreateDefault", fooCreateDefaultType);
		fooCreateDefault.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		
		BasicBlock fooCreateDefaultBody = fooCreateDefault.appendBasicBlock("fooCreateDefaultBody");
		builder.positionBuilderAtEnd(fooCreateDefaultBody);
		
		GetElementPointerInstruction fcdbZero = builder.buildGEP(fooCreateDefault.getParameter(0), 0, "0");
		builder.buildStore(fooVtableData, fcdbZero);
		
		GetElementPointerInstruction fcdbOne = builder.buildGEP(fooCreateDefault.getParameter(0), 1, "1");
		builder.buildStore(ConstantInteger.constI32(0), fcdbOne);
		builder.buildRetVoid();

		//SetLength
		FunctionType fooSetLengthType = new FunctionType(new VoidType(), new Type[]{new PointerType(foo, 0), IntegerType.i32}, false);
		Function fooSetLength = new Function(module, "FooSetLength", fooSetLengthType);
		fooSetLength.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		
		BasicBlock fooSetLenghtBody = fooSetLength.appendBasicBlock("fooSetLenghtBody");
		builder.positionBuilderAtEnd(fooSetLenghtBody);
		
		GetElementPointerInstruction fslOne = builder.buildGEP(fooSetLength.getParameter(0), 1, "1");
		builder.buildStore(fooSetLength.getParameter(1), fslOne);
		builder.buildRetVoid();
		
		//Main
		FunctionType mainType = new FunctionType(IntegerType.i32, new Type[0], false);
		Function main = new Function(module, "main", mainType);
		main.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		
		BasicBlock mainBody = main.appendBasicBlock("mainBody");
		builder.positionBuilderAtEnd(mainBody);
		
		StackAllocation fooAlloc = builder.buildAlloca(foo, "foo");
		builder.buildCall(fooCreateDefault, new Value[]{fooAlloc}, "");
		builder.buildCall(fooSetLength, new Value[]{fooAlloc, ConstantInteger.constI32(29)}, "");
		
		GetElementPointerInstruction mone = builder.buildGEP(fooAlloc, 0, "1");
		LoadInstruction mtwo = builder.buildLoad(mone, "2");
		GetElementPointerInstruction mthree = builder.buildGEP(mtwo, 0, "3");
		LoadInstruction mfour = builder.buildLoad(mthree, "4");
		CallInstruction mfive = builder.buildCall(mfour, new Value[]{fooAlloc}, "5");
		builder.buildRet(mfive);
		
		module.dump();
		
		ExecutionEngine engine = new ExecutionEngine(module);
		GenericValue runFunction = engine.runFunction(main, new GenericValue[0]);
		System.out.println(org.jllvm.bindings.ExecutionEngine.LLVMGenericValueToInt(runFunction.getInstance(), 1));
	}

}
