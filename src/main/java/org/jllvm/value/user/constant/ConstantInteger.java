package org.jllvm.value.user.constant;

import java.math.BigInteger;

import org.jllvm._type.IntegerType;
import org.jllvm._type.PointerType;
import org.jllvm._type.RealType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaqueValue;

/**
 * signExtend default true
 * @author luzj
 */
public class ConstantInteger extends Constant {
	
	public static ConstantInteger constI32(long N) {
		return constInt(IntegerType.i32, N, true);
	}
	
	public static ConstantInteger constI64(long N) {
		return constInt(IntegerType.i32, N, true);
	}
	
	public static ConstantInteger constI16(long N) {
		return constInt(IntegerType.i16, N, true);
	}
	
	public static ConstantInteger constI8(long N) {
		return constInt(IntegerType.i8, N, true);
	}
	
	public static ConstantInteger constI1(long N) {
		return constInt(IntegerType.i1, N, true);
	}
	
	public static ConstantInteger constInt(IntegerType intType,long N,boolean signExtend) {
		if(intType.equals(IntegerType.i1))
			return new ConstantBoolean(N != 0);
		return new ConstantInteger(Core.LLVMConstInt(intType.getInstance(),BigInteger.valueOf(N),signExtend ? 1 : 0));
	}
	
	public ConstantInteger(LLVMOpaqueValue c) {
		super(c);
		assert(typeOf() instanceof IntegerType);
	}
	
	public ConstantExpression truncate(IntegerType targetType) {
		return new ConstantExpression(Core.LLVMConstTrunc(instance,targetType.getInstance()));
	}
	
	public ConstantExpression signExtend(IntegerType targetType) {
		return new ConstantExpression(Core.LLVMConstSExt(instance,targetType.getInstance()));
	}
	
	public ConstantExpression zeroExtend(IntegerType targetType) {
		return new ConstantExpression(Core.LLVMConstZExt(instance,targetType.getInstance()));
	}
	
	public ConstantExpression toFloatingPoint(RealType targetType,boolean signed) {
		if(signed)
			return new ConstantExpression(Core.LLVMConstSIToFP(instance,targetType.getInstance()));
		else
			return new ConstantExpression(Core.LLVMConstUIToFP(instance,targetType.getInstance()));
	}
	
	public ConstantExpression toPointer(PointerType targetType) {
		return new ConstantExpression(Core.LLVMConstIntToPtr(instance,targetType.getInstance()));
	}
}
