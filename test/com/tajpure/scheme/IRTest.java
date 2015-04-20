package com.tajpure.scheme;

import java.math.BigInteger;

import org.jllvm.Context;
import org.jllvm.ExecutionEngine;
import org.jllvm.InstructionBuilder;
import org.jllvm.Module;
import org.jllvm.ModulePassManager;
import org.jllvm.NativeLibrary;
import org.jllvm._type.FunctionType;
import org.jllvm._type.IntegerType;
import org.jllvm._type.Type;
import org.jllvm.bindings.LLVMCallConv;
import org.jllvm.bindings.LLVMIntPredicate;
import org.jllvm.generic.GenericInt;
import org.jllvm.generic.GenericValue;
import org.jllvm.value.Argument;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;
import org.jllvm.value.user.constant.ConstantInteger;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.instruction.BranchInstruction;
import org.jllvm.value.user.instruction.CallInstruction;
import org.jllvm.value.user.instruction.IntegerComparison;
import org.jllvm.value.user.instruction.MultiplyInstruction;
import org.jllvm.value.user.instruction.PhiNode;
import org.jllvm.value.user.instruction.ReturnInstruction;
import org.jllvm.value.user.instruction.SubtractInstruction;

public class IRTest {
	
	public static void main(String[] args) throws Exception {
		NativeLibrary.load();
		
		Context context = Context.getGlobalContext();
		Module module = new Module("com.hoolai.demo", context);
		
		FunctionType functionType = new FunctionType(IntegerType.i32, new Type[]{IntegerType.i32}, false);
		Function fac = new Function(module, "fac", functionType);
		fac.setCallingConvention(LLVMCallConv.LLVMCCallConv);
		InstructionBuilder builder = new InstructionBuilder(context);
		
		Argument n = fac.getParameter(0);

		BasicBlock entry = fac.appendBasicBlock("entry");
		builder.positionBuilderAtEnd(entry);
		
		BasicBlock iftrue = fac.appendBasicBlock("iftrue");
		BasicBlock iffalse = fac.appendBasicBlock("iffalse");
		BasicBlock end = fac.appendBasicBlock("end");
		
		IntegerComparison If = new IntegerComparison(builder, LLVMIntPredicate.LLVMIntEQ, n, ConstantInteger.constI32(1), "param == 1");
		new BranchInstruction(builder, If, iftrue, iffalse);
		
		builder.positionBuilderAtEnd(iftrue);
		ConstantInteger res_iftrue = ConstantInteger.constI32(1);
		new BranchInstruction(builder, end);
		
		builder.positionBuilderAtEnd(iffalse);
		SubtractInstruction n_minus = new SubtractInstruction(builder, n, ConstantInteger.constI32(1), false, "n-1");
		CallInstruction call_function = new CallInstruction(builder, fac, new Value[]{n_minus}, "n * fac(n - 1)");
		
		MultiplyInstruction res_iffalse = new MultiplyInstruction(builder, n, call_function, false, "n * fac(n - 1)");
		new BranchInstruction(builder, end);
		
		builder.positionBuilderAtEnd(end);
		
		PhiNode res = new PhiNode(builder, IntegerType.i32, "result");
		Value[] phi_vals = {res_iftrue, res_iffalse};
		BasicBlock[] phi_blocks = {iftrue, iffalse};
		res.addIncoming(phi_vals, phi_blocks);
		
		new ReturnInstruction(builder, res);
		
		ExecutionEngine engine = new ExecutionEngine(module);
		//TODO module.verify()

		ModulePassManager pass = new ModulePassManager();
		pass.addTargetData(engine.getTargetData());
		pass.run(module);
		
		GenericValue runFunction = engine.runFunction(fac, new GenericValue[]{new GenericInt(IntegerType.i32, new BigInteger("10", 10), true)});
		System.out.println(org.jllvm.bindings.ExecutionEngine.LLVMGenericValueToInt(runFunction.getInstance(), 1));
		
		module.dump();
//		module.writeBitcodeToFile("/Users/luzj/a.ll");
	}
	

}
