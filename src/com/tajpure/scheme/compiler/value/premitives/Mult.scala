package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.Scope
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.value.user.instruction.StackAllocation
import org.jllvm.value.user.instruction.GetElementPointerInstruction
import org.jllvm.value.user.instruction.LoadInstruction

class Mult extends PrimFunc("*", -1) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size == 0) {
      throw new CompilerException("Exception: incorrect arguments count in call '*'", location)
    } 
    else if (args.size == 1) {
      args(0)
    } 
    else {
      args.foldLeft(new IntValue(1).asInstanceOf[Value])((result, arg) => {
        if (result.isInstanceOf[IntValue] && arg.isInstanceOf[IntValue]) {
          new IntValue(result.asInstanceOf[IntValue].value * arg.asInstanceOf[IntValue].value)
        } 
        else if (result.isInstanceOf[IntValue] && arg.isInstanceOf[FloatValue]) {
          new FloatValue(result.asInstanceOf[IntValue].value * arg.asInstanceOf[FloatValue].value)
        } 
        else if (result.isInstanceOf[FloatValue] && arg.isInstanceOf[IntValue]) {
          new FloatValue(result.asInstanceOf[FloatValue].value * arg.asInstanceOf[IntValue].value)
        } 
        else if (result.isInstanceOf[FloatValue] && arg.isInstanceOf[FloatValue]) {
          new FloatValue(result.asInstanceOf[FloatValue].value * arg.asInstanceOf[FloatValue].value)
        } 
        else {
          Log.error(location, "Exception: incorrect arguments in call '*'")
          Value.VOID
        }
      })
    }
  }

  def typecheck(args: List[Value], location: Node): Value = {
    null
  }
  
  def codegen(args: List[Value], location: Node): Value = {
    println("codegen")
    null
  }
  
  override
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    if (args.size != 2) {
      throw new CompilerException("Exception: incorrect arguments count in call '*'", location)
    }
    else if (!args(0).isInstanceOf[StackAllocation] && !args(1).isInstanceOf[StackAllocation]) {
      null
    }
    else if (!args(0).isInstanceOf[StackAllocation] && args(1).isInstanceOf[StackAllocation]) {
      val alloc: StackAllocation = args(0).asInstanceOf[StackAllocation]
      val mone: GetElementPointerInstruction = s.codegen.builder.buildGEP(alloc, 0, "1")
      val _type: LoadInstruction = s.codegen.builder.buildLoad(mone, "2");
      s.codegen.builder.buildMul(_type, args(1), "mult")
    }
    else if (args(0).isInstanceOf[StackAllocation] && !args(1).isInstanceOf[StackAllocation]) {
      val alloc: StackAllocation = args(0).asInstanceOf[StackAllocation]
      val indeces = Array[org.jllvm.value.Value](ConstantInteger.constI32(0), ConstantInteger.constI32(0))
      val mone: GetElementPointerInstruction = s.codegen.builder.buildGEP(alloc, indeces, "1")
      mone.dump()
//      val _type: LoadInstruction = s.codegen.builder.buildLoad(mone, "2")
//      _type.dump()
      s.codegen.builder.buildMul(args(1), args(1), "mult")
    }
    else if (args(0).isInstanceOf[StackAllocation] && args(1).isInstanceOf[StackAllocation]) {
      null
    }
    else {
      null
    }
  }

  override def toString: String = {
    "*"
  }

}