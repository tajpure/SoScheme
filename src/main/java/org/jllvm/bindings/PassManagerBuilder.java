/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.jllvm.bindings;

public class PassManagerBuilder {
  public static LLVMOpaquePassManagerBuilder LLVMPassManagerBuilderCreate() {
    long cPtr = PassManagerBuilderJNI.LLVMPassManagerBuilderCreate();
    return (cPtr == 0) ? null : new LLVMOpaquePassManagerBuilder(cPtr, false);
  }

  public static void LLVMPassManagerBuilderDispose(LLVMOpaquePassManagerBuilder PMB) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderDispose(LLVMOpaquePassManagerBuilder.getCPtr(PMB));
  }

  public static void LLVMPassManagerBuilderSetOptLevel(LLVMOpaquePassManagerBuilder PMB, long OptLevel) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderSetOptLevel(LLVMOpaquePassManagerBuilder.getCPtr(PMB), OptLevel);
  }

  public static void LLVMPassManagerBuilderSetSizeLevel(LLVMOpaquePassManagerBuilder PMB, long SizeLevel) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderSetSizeLevel(LLVMOpaquePassManagerBuilder.getCPtr(PMB), SizeLevel);
  }

  public static void LLVMPassManagerBuilderSetDisableUnitAtATime(LLVMOpaquePassManagerBuilder PMB, int Value) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderSetDisableUnitAtATime(LLVMOpaquePassManagerBuilder.getCPtr(PMB), Value);
  }

  public static void LLVMPassManagerBuilderSetDisableUnrollLoops(LLVMOpaquePassManagerBuilder PMB, int Value) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderSetDisableUnrollLoops(LLVMOpaquePassManagerBuilder.getCPtr(PMB), Value);
  }

  public static void LLVMPassManagerBuilderSetDisableSimplifyLibCalls(LLVMOpaquePassManagerBuilder PMB, int Value) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderSetDisableSimplifyLibCalls(LLVMOpaquePassManagerBuilder.getCPtr(PMB), Value);
  }

  public static void LLVMPassManagerBuilderUseInlinerWithThreshold(LLVMOpaquePassManagerBuilder PMB, long Threshold) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderUseInlinerWithThreshold(LLVMOpaquePassManagerBuilder.getCPtr(PMB), Threshold);
  }

  public static void LLVMPassManagerBuilderPopulateFunctionPassManager(LLVMOpaquePassManagerBuilder PMB, LLVMOpaquePassManager PM) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderPopulateFunctionPassManager(LLVMOpaquePassManagerBuilder.getCPtr(PMB), LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMPassManagerBuilderPopulateModulePassManager(LLVMOpaquePassManagerBuilder PMB, LLVMOpaquePassManager PM) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderPopulateModulePassManager(LLVMOpaquePassManagerBuilder.getCPtr(PMB), LLVMOpaquePassManager.getCPtr(PM));
  }

  public static void LLVMPassManagerBuilderPopulateLTOPassManager(LLVMOpaquePassManagerBuilder PMB, LLVMOpaquePassManager PM, boolean Internalize, boolean RunInliner) {
    PassManagerBuilderJNI.LLVMPassManagerBuilderPopulateLTOPassManager(LLVMOpaquePassManagerBuilder.getCPtr(PMB), LLVMOpaquePassManager.getCPtr(PM), Internalize, RunInliner);
  }

}
