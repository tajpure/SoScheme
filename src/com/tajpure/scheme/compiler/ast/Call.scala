package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Closure
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException

class Call(_op: Node, _args: Argument, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  val op: Node = _op
  
  val args: Argument = _args
  
  def interp(s: Scope): Value = {
    val opValue: Value = this.op.interp(s)
    if (opValue.isInstanceOf[Closure]) {
      val closure: Closure = opValue.asInstanceOf[Closure]
      val funcScope: Scope = new Scope(closure.env)
      val params: List[Name] = closure.func.params
      
      if (closure.properties != null) {
        Scope.mergeDefault(closure.properties, funcScope)
      }
      
      params.zipWithIndex.foreach {
        case (param, i) => 
        val value: Value = args.positional(i).interp(s)
        funcScope.putValue(params(i).id, value)
      }
      
      closure.func.body.interp(funcScope)
    } 
    else if (opValue.isInstanceOf[PrimFunc]) {
      val primFunc = opValue.asInstanceOf[PrimFunc]
      val args: List[Value] = Node.interpList(this.args.positional, s)
      primFunc.apply(args, this)
    } 
    else {
      Log.error(this.op, "this is not a function.")
      Value.VOID
    }
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    val opValue: Value = this.op.interp(s)
    if (opValue.isInstanceOf[Closure]) {
      val closure: Closure = opValue.asInstanceOf[Closure]
      val funcScope: Scope = new Scope(closure.env)
      val params: List[Name] = closure.func.params
      
      if (closure.properties != null) {
        Scope.mergeDefault(closure.properties, funcScope)
      }
      
      params.zipWithIndex.foreach { case (param, i) => 
        val value: Value = args.positional(i).interp(s)
        funcScope.putValue(params(i).id, value)
      }
      
      closure.func.body.codegen(funcScope)
    } 
    else if (opValue.isInstanceOf[PrimFunc]) {
      val primFunc = opValue.asInstanceOf[PrimFunc]
      val args: List[org.jllvm.value.Value] = Node.codegenList(this.args.positional, s)
      primFunc.codegen(args, this, s)
    } 
    else {
      Log.error(this.op, "this is not a function.")
      null
    }
  }
  
  override
  def toString(): String = {
    op + " " + args
  }
  
}