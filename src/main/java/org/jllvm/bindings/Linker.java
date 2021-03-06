/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.jllvm.bindings;

public class Linker {
  public static LLVMOpaqueStringRefArray new_StringArray(int nelements) {
    long cPtr = LinkerJNI.new_StringArray(nelements);
    return (cPtr == 0) ? null : new LLVMOpaqueStringRefArray(cPtr, false);
  }

  public static void delete_StringArray(LLVMOpaqueStringRefArray ary) {
    LinkerJNI.delete_StringArray(LLVMOpaqueStringRefArray.getCPtr(ary));
  }

  public static String StringArray_getitem(LLVMOpaqueStringRefArray ary, int index) {
    return LinkerJNI.StringArray_getitem(LLVMOpaqueStringRefArray.getCPtr(ary), index);
  }

  public static void StringArray_setitem(LLVMOpaqueStringRefArray ary, int index, String value) {
    LinkerJNI.StringArray_setitem(LLVMOpaqueStringRefArray.getCPtr(ary), index, value);
  }

  public static int LLVMLinkModules(LLVMOpaqueModule Dest, LLVMOpaqueModule Src, LLVMLinkerMode Mode, LLVMOpaqueStringRefArray OutMessage) {
    return LinkerJNI.LLVMLinkModules(LLVMOpaqueModule.getCPtr(Dest), LLVMOpaqueModule.getCPtr(Src), Mode.swigValue(), LLVMOpaqueStringRefArray.getCPtr(OutMessage));
  }

}
