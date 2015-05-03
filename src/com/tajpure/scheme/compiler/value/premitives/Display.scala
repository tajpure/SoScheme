package com.tajpure.scheme.compiler.value.premitives

import org.jllvm._type.FunctionType
import org.jllvm._type.Type
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import org.jllvm.value.user.constant.Function
import org.jllvm._type.IntegerType
import org.jllvm._type.PointerType
import org.jllvm.value.user.constant.ConstantString
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.value.user.constant.ConstantPointer

class Display extends PrimFunc("display" , -1) {
  
  def apply(args: List[Value], location: Node): Value = {
    args.foreach { arg => print(arg) }
    Value.VOID
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    val _params: Array[Type] = args.map { arg => new PointerType(IntegerType.i8, 0) }.toArray
    val printf: Function = new Function(s.codegen.module, "printf", new FunctionType(s.codegen.any, _params, false))
    
    val argsPtr = args.map { arg => {
      if (arg.isInstanceOf[ConstantString]) {
        val str = arg.asInstanceOf[ConstantString]
        val strP = s.codegen.builder.buildLoad(str, "str")
        strP.dump()
        s.codegen.builder.buildGEP(strP, Array(0, 0), "strP")
      }
      else {
        arg
      }
    } }
    
    s.codegen.builder.buildCall(printf, argsPtr.toArray, "call")
  }

  override
  def toString: String = {
    "display"
  }
  
}