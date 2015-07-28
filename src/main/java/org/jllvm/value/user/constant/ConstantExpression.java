package org.jllvm.value.user.constant;

import org.jllvm._type.IntegerType;
import org.jllvm._type.Type;
import org.jllvm._type.VectorType;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMIntPredicate;
import org.jllvm.bindings.LLVMRealPredicate;
import org.jllvm.bindings.LLVMOpaqueValue;

public class ConstantExpression extends Constant {
	public enum DivType {SIGNEDINT,UNSIGNEDINT,FLOAT};
	public static ConstantExpression getSizeOf(Type type) {
		return new ConstantExpression(Core.LLVMSizeOf(type.getInstance()));
	}
	
	public static ConstantExpression add(Constant lhs,Constant rhs) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstAdd(lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression subtract(Constant lhs,Constant rhs) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstSub(lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression multiply(Constant lhs,Constant rhs) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstMul(lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression divide(Constant lhs,Constant rhs,DivType type) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		if(type == DivType.SIGNEDINT)
			return new ConstantExpression(Core.LLVMConstSDiv(lhs.instance,rhs.instance));
		else if(type == DivType.UNSIGNEDINT)
			return new ConstantExpression(Core.LLVMConstUDiv(lhs.instance,rhs.instance));
		else if(type == DivType.FLOAT)
			return new ConstantExpression(Core.LLVMConstFDiv(lhs.instance,rhs.instance));
		return null;
	}
	
	public static ConstantExpression remainder(Constant lhs,Constant rhs,DivType type) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		if(type == DivType.SIGNEDINT)
			return new ConstantExpression(Core.LLVMConstSRem(lhs.instance,rhs.instance));
		else if(type == DivType.UNSIGNEDINT)
			return new ConstantExpression(Core.LLVMConstURem(lhs.instance,rhs.instance));
		else if(type == DivType.FLOAT)
			return new ConstantExpression(Core.LLVMConstFRem(lhs.instance,rhs.instance));
		return null;
	}
	
	public static ConstantExpression or(Constant lhs,Constant rhs) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstOr(lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression and(Constant lhs,Constant rhs) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstAnd(lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression xor(Constant lhs,Constant rhs) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstXor(lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression intComparison(Constant lhs,Constant rhs,LLVMIntPredicate pred) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstICmp(pred,lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression realComparison(Constant lhs,Constant rhs,LLVMRealPredicate pred) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstFCmp(pred,lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression shiftLeft(Constant lhs,Constant rhs) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		return new ConstantExpression(Core.LLVMConstShl(lhs.instance,rhs.instance));
	}
	
	/* Pass in false for a logical shift-right (zero-fill) and true for an arithmetic shift-right (sign-extension). */
	public static ConstantExpression shiftRight(Constant lhs,Constant rhs,boolean arithmetic) {
		assert(lhs.typeOf().equals(rhs.typeOf()));
		if(arithmetic)
			return new ConstantExpression(Core.LLVMConstAShr(lhs.instance,rhs.instance));
		else
			return new ConstantExpression(Core.LLVMConstLShr(lhs.instance,rhs.instance));
	}
	
	public static ConstantExpression select(Constant condition,Constant True,Constant False) {
		assert(condition instanceof ConstantVector || condition instanceof ConstantBoolean);
		assert(True.typeOf().equals(False.typeOf()));
		return new ConstantExpression(Core.LLVMConstSelect(condition.instance,True.instance,False.instance));	
	}
	
	public static ConstantExpression shuffleVector(ConstantVector a,ConstantVector b,ConstantVector mask) {
		assert(mask.typeOf() instanceof VectorType && ((VectorType)mask.typeOf()).getElementType().equals(IntegerType.i32));
		return new ConstantExpression(Core.LLVMConstShuffleVector(a.instance,b.instance,mask.instance));
	}
	
	public ConstantExpression(LLVMOpaqueValue val) {
		super(val);
	}		
}
