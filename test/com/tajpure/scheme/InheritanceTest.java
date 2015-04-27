package com.tajpure.scheme;

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
import org.jllvm.value.user.constant.ConstantInteger;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.instruction.BitCast;
import org.jllvm.value.user.instruction.CallInstruction;
import org.jllvm.value.user.instruction.GetElementPointerInstruction;
import org.jllvm.value.user.instruction.LoadInstruction;
import org.jllvm.value.user.instruction.StackAllocation;

/**
 * 单继承测试
 * @author luzj
 */
public class InheritanceTest {
	
	public static void main(String[] args) throws Exception {
		NativeLibrary.load();
		
		IdentifiedStructType base = new IdentifiedStructType("Base");
		PointerType basePtr = new PointerType(base, 0);
		base.setBody(new Type[]{IntegerType.i32}, false);
		
		Module module = new Module("com.hoolai.compiler.InheritanceTest");
		InstructionBuilder builder = new InstructionBuilder();
		
	
		FunctionType baseSetAType = new FunctionType(new VoidType(), new Type[]{basePtr, IntegerType.i32}, false);
		Function baseSetA = new Function(module, "BaseSetA", baseSetAType);
		baseSetA.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		BasicBlock baseSetABody = baseSetA.appendBasicBlock();
		builder.positionBuilderAtEnd(baseSetABody);
		
		GetElementPointerInstruction baseOne = builder.buildGEP(baseSetA.getParameter(0), 0, "1");
		builder.buildStore(baseSetA.getParameter(1), baseOne);
		builder.buildRetVoid();
		
		FunctionType baseGetAType = new FunctionType(IntegerType.i32, new Type[]{basePtr}, false);
		Function baseGetA = new Function(module, "BaseGetA", baseGetAType);
		baseGetA.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		BasicBlock baseGetABody = baseGetA.appendBasicBlock();
		builder.positionBuilderAtEnd(baseGetABody);
		GetElementPointerInstruction baseGetOne = builder.buildGEP(baseGetA.getParameter(0), 0, "1");
		LoadInstruction baseRetOne = builder.buildLoad(baseGetOne, "2");
		builder.buildRet(baseRetOne);
		
		IdentifiedStructType derived = new IdentifiedStructType("Derived");
		PointerType derivedPtr = new PointerType(derived, 0);
		derived.setBody(new Type[]{IntegerType.i32, IntegerType.i32}, false);
		
		FunctionType derivedSetBType = new FunctionType(new VoidType(), new Type[]{derivedPtr, IntegerType.i32}, false);
		Function derivedSetB = new Function(module, "DerivedSetB", derivedSetBType);
		derivedSetB.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		BasicBlock derivedSetABody = derivedSetB.appendBasicBlock("body");
		builder.positionBuilderAtEnd(derivedSetABody);
		
		BitCast baseVPtr = builder.buildBitCast(derivedSetB.getParameter(0), new PointerType(base, 0), "Base");
		builder.buildCall(baseSetA, new Value[]{baseVPtr, derivedSetB.getParameter(1)}, "");
		
		GetElementPointerInstruction derivedOne = builder.buildGEP(derivedSetB.getParameter(0), 1, "1");
		builder.buildStore(derivedSetB.getParameter(1), derivedOne);
		builder.buildRetVoid();
		
		FunctionType mainType = new FunctionType(IntegerType.i32, new Type[0], false);
		Function main = new Function(module, "main", mainType);
		main.setLinkage(LLVMLinkage.LLVMExternalLinkage);
		
		BasicBlock mainBody = main.appendBasicBlock("mainBody");
		builder.positionBuilderAtEnd(mainBody);
		StackAllocation derivedVal = builder.buildAlloca(derived, "derived");
		builder.buildCall(derivedSetB, new Value[]{derivedVal, ConstantInteger.constI32(121)}, "");
		BitCast baseVVPtr = builder.buildBitCast(derivedVal, new PointerType(base, 0), "Base");
		CallInstruction retVal = builder.buildCall(baseGetA, new Value[]{baseVVPtr}, "ret");
		builder.buildRet(retVal);
		
		module.dump();
		
		ExecutionEngine engine = new ExecutionEngine(module);
		GenericValue runFunction = engine.runFunction(main, new GenericValue[0]);
		System.out.println(org.jllvm.bindings.ExecutionEngine.LLVMGenericValueToInt(runFunction.getInstance(), 1));
	}
	
}
