/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.jllvm.bindings;

public class Scalar {
  public static void LLVMAddAggressiveDCEPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddAggressiveDCEPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddCFGSimplificationPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddCFGSimplificationPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddDeadStoreEliminationPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddDeadStoreEliminationPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddGVNPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddGVNPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddIndVarSimplifyPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddIndVarSimplifyPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddInstructionCombiningPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddInstructionCombiningPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddJumpThreadingPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddJumpThreadingPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddLICMPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddLICMPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddLoopDeletionPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddLoopDeletionPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddLoopIdiomPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddLoopIdiomPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddLoopRotatePass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddLoopRotatePass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddLoopUnrollPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddLoopUnrollPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddLoopUnswitchPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddLoopUnswitchPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddMemCpyOptPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddMemCpyOptPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddPromoteMemoryToRegisterPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddPromoteMemoryToRegisterPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddReassociatePass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddReassociatePass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddSCCPPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddSCCPPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddScalarReplAggregatesPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddScalarReplAggregatesPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddScalarReplAggregatesPassSSA(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddScalarReplAggregatesPassSSA(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddScalarReplAggregatesPassWithThreshold(LLVMOpaquePassManager PM, int Threshold) {
    ScalarJNI.LLVMAddScalarReplAggregatesPassWithThreshold(LLVMOpaquePassManager.getCPtr(PM), Threshold);
  }

  public static void LLVMAddSimplifyLibCallsPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddSimplifyLibCallsPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddTailCallEliminationPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddTailCallEliminationPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddConstantPropagationPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddConstantPropagationPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddDemoteMemoryToRegisterPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddDemoteMemoryToRegisterPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddVerifierPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddVerifierPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddCorrelatedValuePropagationPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddCorrelatedValuePropagationPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddEarlyCSEPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddEarlyCSEPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddLowerExpectIntrinsicPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddLowerExpectIntrinsicPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddTypeBasedAliasAnalysisPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddTypeBasedAliasAnalysisPass(LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMAddBasicAliasAnalysisPass(LLVMOpaquePassManager PM) {
    ScalarJNI.LLVMAddBasicAliasAnalysisPass(LLVMOpaquePassManager.getCPtr(PM));
  }

}
