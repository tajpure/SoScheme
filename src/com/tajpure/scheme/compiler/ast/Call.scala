package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Closure
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import org.jllvm.value.user.instruction.StackAllocation
import com.tajpure.scheme.compiler.value.IntValue
import org.jllvm.value.user.constant.ConstantInteger
import org.jllvm.value.user.instruction.GetElementPointerInstruction
import com.tajpure.scheme.compiler.value.premitives.ListFunc

class Call(_op: Node, _args: Argument, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  def this(_op: Node, _args: Argument, node: Node) = 
    this(_op, _args, node.file, node.start, node.end, node.row, node.col)
    
  val op: Node = _op
  
  val args: Argument = _args
  
  def interp(s: Scope): Value = {
    val opValue: Value = this.op.interp(s)
    if (opValue.isInstanceOf[Closure]) {
      val closure: Closure = opValue.asInstanceOf[Closure]
      val funcScope: Scope = new Scope(closure.env)
      val funcParams: List[Name] = closure.func.params
      
      if (closure.properties != null) {
        Scope.mergeDefault(closure.properties, funcScope)
      }
      
      funcParams.zipWithIndex.foreach {
        case (param, i) => {
          val value: Value = if (funcParams.size - 1 == i) {
              val restArgsVal = Node.interpList(args.positional.slice(i, args.elements.size), s)
              new ListFunc().apply(restArgsVal, this)
            } else {
              args.positional(i).interp(s)
            }
          funcScope.putValue(funcParams(i).id, value)
        }
      }
      
      closure.func.body.interp(funcScope)
    } 
    else if (opValue.isInstanceOf[PrimFunc]) {
      val primFunc = opValue.asInstanceOf[PrimFunc]
      val args: List[Value] = Node.interpList(this.args.positional, s)
      primFunc.apply(args, this)
    } 
    else {
      throw new CompilerException("It's not a function", this.op)
    }
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    val opValue: Value = this.op.interp(s)
    if (opValue.isInstanceOf[Closure]) {
      val closure: Closure = opValue.asInstanceOf[Closure]
      val params: List[Name] = closure.func.params
      
      val func = this.op.codegen(s)
      val _params =   params.zipWithIndex.map { case (param, i) => 
        args.positional(i).codegen(s)
      }
      
      s.codegen.builder.buildCall(func, _params.toArray, "call")
    } 
    else if (opValue.isInstanceOf[PrimFunc]) {
      val primFunc = opValue.asInstanceOf[PrimFunc]
      val unconvertedArgs: List[org.jllvm.value.Value] = Node.codegenList(this.args.positional, s)
      val convertedArgs = argsTypeConvert(this.args.positional, unconvertedArgs, s)
      
      primFunc.codegen(convertedArgs, this, s)
    } 
    else {
      throw new CompilerException("It's not a function", this.op)
    }
  }
  
  def argsTypeConvert(origin: List[Node], unconverted: List[org.jllvm.value.Value], s: Scope): List[org.jllvm.value.Value] = {
    unconverted.map { arg => {
        if (arg.isInstanceOf[GetElementPointerInstruction]) {
          s.codegen.builder.buildLoad(arg, "load")
        }
        else {
          arg
        }
   } }
  }
  
  override
  def toString(): String = {
    op + args.toString()
  }
  
}