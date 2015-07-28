package org.jllvm;

import java.util.ArrayList;
import java.util.List;

import org.jllvm.bindings.Core;
import org.jllvm.bindings.LLVMOpaquePassManager;
import org.jllvm.bindings.Scalar;
import org.jllvm.bindings.Target;

public abstract class PassManager {
	private List<TargetData> datas;

	protected LLVMOpaquePassManager instance;

	public LLVMOpaquePassManager getInstance() {
		return instance;
	}

	public void addTargetData(TargetData target) {
		if (datas == null) {
			datas = new ArrayList<TargetData>();
		}
		datas.add(target);
		Target.LLVMAddTargetData(target.getInstance(), instance);
	}

	public void addAggressiveDCEPass() {
		Scalar.LLVMAddAggressiveDCEPass(instance);
	}

	public void addCFGSimplificationPass() {
		Scalar.LLVMAddCFGSimplificationPass(instance);
	}

	public void addDeadStoreEliminationPass() {
		Scalar.LLVMAddDeadStoreEliminationPass(instance);
	}

	public void addGVNPass() {
		Scalar.LLVMAddGVNPass(instance);
	}

	public void addIndVarSimplifyPass() {
		Scalar.LLVMAddIndVarSimplifyPass(instance);
	}

	public void addInstructionCombiningPass() {
		Scalar.LLVMAddInstructionCombiningPass(instance);
	}

	public void addJumpThreadingPass() {
		Scalar.LLVMAddJumpThreadingPass(instance);
	}

	public void addLICMPass() {
		Scalar.LLVMAddLICMPass(instance);
	}

	public void addLoopDeletionPass() {
		Scalar.LLVMAddLoopDeletionPass(instance);
	}

	public void addLoopIdiomPass() {
		Scalar.LLVMAddLoopIdiomPass(instance);
	}

	public void addLoopRotatePass() {
		Scalar.LLVMAddLoopRotatePass(instance);
	}

	public void addLoopUnrollPass() {
		Scalar.LLVMAddLoopUnrollPass(instance);
	}

	public void addLoopUnswitchPass() {
		Scalar.LLVMAddLoopUnswitchPass(instance);
	}

	public void addMemCpyOptPass() {
		Scalar.LLVMAddMemCpyOptPass(instance);
	}

	public void addPromoteMemoryToRegisterPass() {
		Scalar.LLVMAddPromoteMemoryToRegisterPass(instance);
	}

	public void addReassociatePass() {
		Scalar.LLVMAddReassociatePass(instance);
	}

	public void addSCCPPass() {
		Scalar.LLVMAddSCCPPass(instance);
	}

	public void addScalarReplAggregatesPass() {
		Scalar.LLVMAddScalarReplAggregatesPass(instance);
	}

	public void addScalarReplAggregatesPassSSA() {
		Scalar.LLVMAddScalarReplAggregatesPassSSA(instance);
	}

	public void addScalarReplAggregatesPassWithThreshold(int threshold) {
		Scalar.LLVMAddScalarReplAggregatesPassWithThreshold(instance, threshold);
	}

	public void addSimplifyLibCallsPass() {
		Scalar.LLVMAddSimplifyLibCallsPass(instance);
	}

	public void addTailCallEliminationPass() {
		Scalar.LLVMAddTailCallEliminationPass(instance);
	}

	public void addConstantPropagationPass() {
		Scalar.LLVMAddConstantPropagationPass(instance);
	}

	public void addDemoteMemoryToRegisterPass() {
		Scalar.LLVMAddDemoteMemoryToRegisterPass(instance);
	}

	public void addVerifierPass() {
		Scalar.LLVMAddVerifierPass(instance);
	}

	public void addCorrelatedValuePropagationPass() {
		Scalar.LLVMAddCorrelatedValuePropagationPass(instance);
	}

	public void addEarlyCSEPass() {
		Scalar.LLVMAddEarlyCSEPass(instance);
	}

	public void addLowerExpectIntrinsicPass() {
		Scalar.LLVMAddLowerExpectIntrinsicPass(instance);
	}

	public void addTypeBasedAliasAnalysisPass() {
		Scalar.LLVMAddTypeBasedAliasAnalysisPass(instance);
	}

	public void addBasicAliasAnalysisPass() {
		Scalar.LLVMAddBasicAliasAnalysisPass(instance);
	}

	protected void finalize() {
		Core.LLVMDisposePassManager(instance);
	}
}
