package org.jllvm;

import java.util.HashMap;

import org.jllvm._type.IntegerType;
import org.jllvm._type.RealType;
import org.jllvm._type.Type;
import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMIntPredicate;
import org.jllvm.bindings.LLVMOpaqueBuilder;
import org.jllvm.bindings.LLVMRealPredicate;
import org.jllvm.value.BasicBlock;
import org.jllvm.value.Value;
import org.jllvm.value.user.constant.ConstantInteger;
import org.jllvm.value.user.constant.Function;
import org.jllvm.value.user.instruction.AddInstruction;
import org.jllvm.value.user.instruction.BinaryBitwiseInstruction;
import org.jllvm.value.user.instruction.BinaryBitwiseInstruction.BinaryBitwiseInstructionType;
import org.jllvm.value.user.instruction.BitCast;
import org.jllvm.value.user.instruction.BranchInstruction;
import org.jllvm.value.user.instruction.CallInstruction;
import org.jllvm.value.user.instruction.DivideInstruction;
import org.jllvm.value.user.instruction.DivideInstruction.DivisionType;
import org.jllvm.value.user.instruction.ExtendCast;
import org.jllvm.value.user.instruction.ExtendCast.ExtendType;
import org.jllvm.value.user.instruction.ExtractElementInstruction;
import org.jllvm.value.user.instruction.ExtractValueInstruction;
import org.jllvm.value.user.instruction.FloatComparison;
import org.jllvm.value.user.instruction.FloatToIntegerCast;
import org.jllvm.value.user.instruction.FloatToIntegerCast.FPToIntCastType;
import org.jllvm.value.user.instruction.FreeInstruction;
import org.jllvm.value.user.instruction.GetElementPointerInstruction;
import org.jllvm.value.user.instruction.HeapAllocation;
import org.jllvm.value.user.instruction.InsertElementInstruction;
import org.jllvm.value.user.instruction.InsertValueInstruction;
import org.jllvm.value.user.instruction.Instruction;
import org.jllvm.value.user.instruction.IntegerComparison;
import org.jllvm.value.user.instruction.IntegerToFloatCast;
import org.jllvm.value.user.instruction.ShuffleVectorInstruction;
import org.jllvm.value.user.instruction.IntegerToFloatCast.IntCastType;
import org.jllvm.value.user.instruction.InvokeInstruction;
import org.jllvm.value.user.instruction.LoadInstruction;
import org.jllvm.value.user.instruction.MultiplyInstruction;
import org.jllvm.value.user.instruction.PhiNode;
import org.jllvm.value.user.instruction.PtrIntCast;
import org.jllvm.value.user.instruction.PtrIntCast.PtrIntCastType;
import org.jllvm.value.user.instruction.RemainderInstruction;
import org.jllvm.value.user.instruction.RemainderInstruction.RemainderType;
import org.jllvm.value.user.instruction.ReturnInstruction;
import org.jllvm.value.user.instruction.SelectInstruction;
import org.jllvm.value.user.instruction.ShiftInstruction;
import org.jllvm.value.user.instruction.ShiftInstruction.ShiftType;
import org.jllvm.value.user.instruction.StackAllocation;
import org.jllvm.value.user.instruction.StoreInstruction;
import org.jllvm.value.user.instruction.SubtractInstruction;
import org.jllvm.value.user.instruction.SwitchInstruction;
import org.jllvm.value.user.instruction.TruncateInstruction;
import org.jllvm.value.user.instruction.TruncateInstruction.TruncateType;
import org.jllvm.value.user.instruction.UnaryBitwiseInstruction;
import org.jllvm.value.user.instruction.UnaryBitwiseInstruction.UnaryBitwiseInstructionType;
import org.jllvm.value.user.instruction.UnreachableInstruction;
import org.jllvm.value.user.instruction.VariableArgumentInstruction;

public class InstructionBuilder {
	protected LLVMOpaqueBuilder instance;
	protected static HashMap<LLVMOpaqueBuilder, InstructionBuilder> instr_builders;
	
	public InstructionBuilder() {
		if(instr_builders == null)
			instr_builders = new HashMap<LLVMOpaqueBuilder,InstructionBuilder>();
		instance = Core.LLVMCreateBuilder();
		instr_builders.put(instance,this);
	}
	
	public InstructionBuilder(Context context) {
		if(instr_builders == null)
			instr_builders = new HashMap<LLVMOpaqueBuilder,InstructionBuilder>();
		instance = Core.LLVMCreateBuilderInContext(context.instance);
		instr_builders.put(instance, this);
	}
	
	public void positionBuilder(BasicBlock block,Instruction instr) {
		Core.LLVMPositionBuilder(instance,block.getBBInstance(),instr.getInstance());
	}
	
	public void positionBuilderBefore(Instruction instr) {
		Core.LLVMPositionBuilderBefore(instance,instr.getInstance());
	}
	
	public void positionBuilderAtEnd(BasicBlock block) {
		Core.LLVMPositionBuilderAtEnd(instance,block.getBBInstance());
	}
	
	public BasicBlock getInsertBlock() {
		return BasicBlock.getBasicBlock(Core.LLVMGetInsertBlock(instance));
	}
	
	public void clearInsertionPosition() {
		Core.LLVMClearInsertionPosition(instance);
	}
	
	public void insertIntoBuilder(Instruction instr) {
		Core.LLVMInsertIntoBuilder(instance,instr.getInstance());
	}
	
	public LLVMOpaqueBuilder getInstance() {
		return instance;
	}
	
	public static InstructionBuilder getBuilder(LLVMOpaqueBuilder b) {
		if(instr_builders == null)
			instr_builders = new HashMap<LLVMOpaqueBuilder,InstructionBuilder>();
		assert(b != null);
		return instr_builders.get(b);
	}
	
	protected void finalize() {
		Core.LLVMDisposeBuilder(instance);
	}
	
	public ReturnInstruction buildRetVoid() {
        return new ReturnInstruction(this);
    }
    public ReturnInstruction buildRet(Value val) {
        return new ReturnInstruction(this, val);
    }
    public void buildAggregateRet() {
        //TODO
    }
    public BranchInstruction buildBr(BasicBlock destination) {
    	return new BranchInstruction(this, destination);
    }
    public BranchInstruction buildCondBr(Value condition,BasicBlock thenBlock,BasicBlock elseBlock) {
    	return new BranchInstruction(this, condition, thenBlock, elseBlock);
    }
    public SwitchInstruction buildSwitch(Value value,BasicBlock block,long numCases) {
    	return new SwitchInstruction(this, value, block, numCases);
    }
    public void buildIndirectBr() {
    	//TODO
    }
    public InvokeInstruction buildInvoke(Function func,Value[] arguments,BasicBlock destination,BasicBlock unwind,String name) {
    	return new InvokeInstruction(this, func, arguments, destination, unwind, name);
    }
    public void buildLandingPad() {
    	//TODO
    }
    public void buildResume() {
    	//TODO
    }
    public UnreachableInstruction buildUnreachable() {
    	return new UnreachableInstruction(this);
    }
	public AddInstruction buildAdd(Value lhs, Value rhs, String name) {
    	return new AddInstruction(this, lhs, rhs, false, name);
    }
    public void buildNSWAdd() {
    	//TODO
    }
    public void buildNUWAdd() {
    	//TODO
    }
    public AddInstruction buildFAdd(Value lhs, Value rhs, String name) {
    	return new AddInstruction(this, lhs, rhs, true, name);
    }
    public SubtractInstruction buildSub(Value lhs, Value rhs, String name) {
    	return new SubtractInstruction(this, lhs, rhs, false, name);
    }
    public void buildNSWSub() {
    	//TODO
    }
    public void buildNUWSub() {
    	//TODO
    }
    public SubtractInstruction buildFSub(Value lhs, Value rhs, String name) {
    	return new SubtractInstruction(this, lhs, rhs, true, name);
    }
    public MultiplyInstruction buildMul(Value lhs, Value rhs, String name) {
    	return new MultiplyInstruction(this, lhs, rhs, false, name);
    }
    public void buildNSWMul() {
    	//TODO
    }
    public void buildNUWMul() {
    	//TODO
    }
    public MultiplyInstruction buildFMul(Value lhs, Value rhs, String name) {
    	return new MultiplyInstruction(this, lhs, rhs, true, name);
    }
    public DivideInstruction buildUDiv(Value lhs, Value rhs, String name) {
    	return new DivideInstruction(this, lhs, rhs, DivisionType.UNSIGNEDINT, name);
    }
    public DivideInstruction buildSDiv(Value lhs, Value rhs, String name) {
    	return new DivideInstruction(this, lhs, rhs, DivisionType.SIGNEDINT, name);
    }
    public void buildExactSDiv() {
    	//TODO
    }
    public DivideInstruction buildFDiv(Value lhs, Value rhs, String name) {
    	return new DivideInstruction(this, lhs, rhs, DivisionType.FLOAT, name);
    }
    public RemainderInstruction buildURem(Value lhs, Value rhs, String name) {
    	return new RemainderInstruction(this, lhs, rhs, RemainderType.UNSIGNEDINT, name);
    }
    public RemainderInstruction buildSRem(Value lhs, Value rhs, String name) {
    	return new RemainderInstruction(this, lhs, rhs, RemainderType.SIGNEDINT, name);
    }
    public RemainderInstruction buildFRem(Value lhs, Value rhs, String name) {
    	return new RemainderInstruction(this, lhs, rhs, RemainderType.FLOAT, name);
    }
    public ShiftInstruction buildShl(Value lhs, Value rhs, String name) {
    	return new ShiftInstruction(ShiftType.SHL, this, lhs, rhs, name);
    }
    public ShiftInstruction buildLShr(Value lhs, Value rhs, String name) {
    	return new ShiftInstruction(ShiftType.LOGICAL_SHR, this, lhs, rhs, name);
    }
    public ShiftInstruction buildAShr(Value lhs, Value rhs, String name) {
    	return new ShiftInstruction(ShiftType.ARITHMETIC_SHR, this, lhs, rhs, name);
    }
    public BinaryBitwiseInstruction buildAnd(Value lhs, Value rhs, String name) {
    	return new BinaryBitwiseInstruction(BinaryBitwiseInstructionType.AND, this, lhs, rhs, name);
    }
    public BinaryBitwiseInstruction buildOr(Value lhs, Value rhs, String name) {
    	return new BinaryBitwiseInstruction(BinaryBitwiseInstructionType.OR, this, lhs, rhs, name);
    }
    public BinaryBitwiseInstruction buildXor(Value lhs, Value rhs, String name) {
    	return new BinaryBitwiseInstruction(BinaryBitwiseInstructionType.XOR, this, lhs, rhs, name);
    }
    public void buildBinOp() {
    	//TODO
    }
    public UnaryBitwiseInstruction buildNeg(Value val, String name) {
    	return new UnaryBitwiseInstruction(UnaryBitwiseInstructionType.NEGATIVE, this, val, name);
    }
    public void buildNSWNeg(Value val,String name) {
    	//TODO
    }
    public void buildNUWNeg(Value val, String name) {
    	//TODO
    }
    public void buildFNeg(Value val, String name) {
    	//TODO
    }
    public UnaryBitwiseInstruction buildNot(Value val, String name) {
    	return new UnaryBitwiseInstruction(UnaryBitwiseInstructionType.NOT, this, val, name);
    }
    public HeapAllocation buildMalloc(Type type, String name) {
    	return new HeapAllocation(this, type, name);
    }
    public HeapAllocation buildArrayMalloc(Type type, Value number, String name) {
    	return new HeapAllocation(this, type, number, name);
    }
    public HeapAllocation buildArrayMalloc(Type type, int number, String name) {
    	return buildArrayMalloc(type, ConstantInteger.constI32(number), name);
    }
    public StackAllocation buildAlloca(Type type, String name) {
    	return new StackAllocation(this, type, name);
    }
    public StackAllocation buildArrayAlloca(Type type, Value number, String name) {
    	return new StackAllocation(this, type, number, name);
    }
    public StackAllocation buildArrayAlloca(Type type, int number, String name) {
    	return new StackAllocation(this, type, ConstantInteger.constI32(number), name);
    }
    public FreeInstruction buildFree(Value pointerValue) {
    	return new FreeInstruction(this, pointerValue);
    }
    public LoadInstruction buildLoad(Value pointer,String name) {
    	return new LoadInstruction(this, pointer, name);
    }
    public StoreInstruction buildStore(Value value,Value pointer) {
    	return new StoreInstruction(this, value, pointer);
    }
    public GetElementPointerInstruction buildGEP(Value pointer,Value indices[],String name) {
    	return new GetElementPointerInstruction(this, pointer, indices, name);
    }
    public GetElementPointerInstruction buildGEP(Value pointer,int indices[],String name) {
    	Value[] tmp = new Value[indices.length];
    	for (int i = 0; i < tmp.length; i++) {
			tmp[i] = ConstantInteger.constI32(indices[i]);
		}
    	return buildGEP(pointer, tmp, name);
    }
    public GetElementPointerInstruction buildGEP(Value pointer,int index,String name) {
    	Value[] tmp = new Value[]{ConstantInteger.constI32(0), ConstantInteger.constI32(index)};
    	return buildGEP(pointer, tmp, name);
    }
    public void buildInBoundsGEP() {
    	//TODO
    }
    public void buildStructGEP() {
    	//TODO
    }
    public void buildGlobalString() {
    	//TODO
    }
    public void buildGlobalStringPtr() {
    	//TODO
    }
    public TruncateInstruction buildTrunc(Value val, Type destType, String name) {
    	return new TruncateInstruction(TruncateType.INTEGER, this, val, destType, name);
    }
    public ExtendCast buildZExt(Value val, Type destType, String name) {
    	return new ExtendCast(ExtendType.ZERO, this, val, destType, name);
    }
    public ExtendCast buildSExt(Value val, Type destType, String name) {
    	return new ExtendCast(ExtendType.SIGN, this, val, destType, name);
    }
    public FloatToIntegerCast buildFPToUI(Value val, IntegerType destType, String name) {
    	return new FloatToIntegerCast(this, val, destType, name, FPToIntCastType.UNSIGNED);
    }
    public FloatToIntegerCast buildFPToSI(Value val, IntegerType destType, String name) {
    	return new FloatToIntegerCast(this, val, destType, name, FPToIntCastType.SIGNED);
    }
    public IntegerToFloatCast buildUIToFP(Value val, RealType destType, String name) {
    	return new IntegerToFloatCast(this, val, destType, name, IntCastType.UNSIGNED);
    }
    public IntegerToFloatCast buildSIToFP(Value val, RealType destType, String name) {
    	return new IntegerToFloatCast(this, val, destType, name, IntCastType.SIGNED);
    }
    public TruncateInstruction buildFPTrunc(Value val, Type destType, String name) {
    	return new TruncateInstruction(TruncateType.FLOAT, this, val, destType, name);
    }
    public ExtendCast buildFPExt(Value val, Type destType, String name) {
    	return new ExtendCast(ExtendType.FLOAT, this, val, destType, name);
    }
    public PtrIntCast buildPtrToInt(Value val, Type destType, String name) {
    	return new PtrIntCast(this, val, destType, name, PtrIntCastType.PTR_TO_INT);
    }
    public PtrIntCast buildIntToPtr(Value val, Type destType, String name) {
    	return new PtrIntCast(this, val, destType, name, PtrIntCastType.INT_TO_PTR);
    }
    public BitCast buildBitCast(Value val, Type destType, String name) {
    	return new BitCast(this, val, destType, name);
    }
    public void buildZExtOrBitCast() {
    	//TODO
    }
    public void buildSExtOrBitCast() {
    	//TODO
    }
    public void buildTruncOrBitCast() {
    	//TODO
    }
    public void buildCast() {
    	//TODO
    }
    public void buildPointerCast() {
    	//TODO
    }
    public void buildIntCast() {
    	//TODO
    }
    public void buildFPCast() {
    	//TODO
    }
    public IntegerComparison buildICmp(LLVMIntPredicate Op,Value lhs,Value rhs,String name) {
    	return new IntegerComparison(this, Op, lhs, rhs, name);
    }
    public FloatComparison buildFCmp(LLVMRealPredicate Op,Value lhs,Value rhs,String name) {
    	return new FloatComparison(this, Op, lhs, rhs, name);
    }
    public PhiNode buildPhi(Type type,String name) {
    	return new PhiNode(this, type, name);
    }
    public CallInstruction buildCall(Value func,Value[] arguments,String name) {
    	return new CallInstruction(this, func, arguments, name);
    }
    public SelectInstruction buildSelect(Value condition,Value then,Value otherwise,String name) {
    	return new SelectInstruction(this, condition, then, otherwise, name);
    }
    public VariableArgumentInstruction buildVAArg(Value valist,Type vatype,String name) {
    	return new VariableArgumentInstruction(this, valist, vatype, name);
    }
    public ExtractElementInstruction buildExtractElement(Value vector,Value index,String name) {
    	return new ExtractElementInstruction(this, vector, index, name);
    }
    public InsertElementInstruction buildInsertElement(Value vector,Value element,Value index,String name) {
    	return new InsertElementInstruction(this, vector, element, index, name);
    }
    public ShuffleVectorInstruction buildShuffleVector(Value vec1,Value vec2,Value mask,String name) {
    	return new ShuffleVectorInstruction(this, vec1, vec2, mask, name);
    }
    public ExtractValueInstruction buildExtractValue(Value aggr,long index,String name) {
    	return new ExtractValueInstruction(this, aggr, index, name);
    }
    public InsertValueInstruction buildInsertValue(Value aggr,Value Value,long index,String name) {
    	return new InsertValueInstruction(this, aggr, Value, index, name);
    }
    public void buildIsNull() {
    	//TODO
    }
    public void buildIsNotNull() {
    	//TODO
    }
    public void buildPtrDiff() {
    	//TODO
    }
}
