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
import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.exception.RunTimeException
import com.tajpure.scheme.compiler.value.premitives.Import
import com.tajpure.scheme.compiler.value.premitives.FilterFunc
import scala.collection.mutable.HashMap
import com.tajpure.scheme.compiler.util.LRUCache

class Call(val op: Node, val args: Argument, _file: String, _start: Int, _end: Int, _row: Int, _col: Int)
  extends Node(_file, _start, _end, _row, _col) {
  
  def this(_op: Node, _args: Argument, node: Node) = 
    this(_op, _args, node.file, node.start, node.end, node.row, node.col)
    
  def interp(s: Scope): Value = {
    val value = CallMemory.lookup(getSign(s))
    val result = if (!value.isEmpty) {
        value.get
      }
      else {
        val opValue: Value = this.op.interp(s)
        
        if (opValue.isInstanceOf[Closure]) {
          val closure: Closure = opValue.asInstanceOf[Closure]
          val funcScope: Scope = new Scope(closure.env)
          val funcParams: Node = closure.func.params
          
          if (closure.properties != null) {
            Scope.mergeDefault(closure.properties, funcScope)
          }
          
          if (funcParams.isInstanceOf[Tuple]) {
            val params = funcParams.asInstanceOf[Tuple].elements.map { node => node.asInstanceOf[Name] }
            params.zipWithIndex.foreach {
              case (param, i) => {
                if (params.size - 2 == i && Constants.DOT.equals(param.id)) {
                  val restArgsVal = Node.interpList(args.positional.slice(i, args.elements.size), s)
                  val value = new ListFunc().apply(restArgsVal, this)
                  funcScope.putValue(params(i + 1).id, value)
                }
                else if (i > 0 && params.size - 1 == i && Constants.DOT.equals(params(i - 1).id)) {
                  // When the arguments like "(x y . z)", we need to avoid "z" being reset.
                }
                else if (i < params.size && params.size <= args.elements.size) {
                    val value = args.positional(i).interp(s)
                    funcScope.putValue(params(i).id, value)
                }  
                else {
                  throw new RunTimeException("incorrent argument count in call", this)
                }
              }
            }
          }
          else if (funcParams.isInstanceOf[Name]) {
            val restArgsVal = Node.interpList(args.positional, s)
            val value = new ListFunc().apply(restArgsVal, this)
            funcScope.putValue(funcParams.asInstanceOf[Name].id, value)
          }
          else {
            throw new RunTimeException("incorrent argument", this)
          }
          
          closure.func.body.interp(funcScope)
        } 
        else if (opValue.isInstanceOf[PrimFunc]) {
          val primFunc = opValue.asInstanceOf[PrimFunc]
          val args: List[Value] = Node.interpList(this.args.positional, s)
          if (!opValue.isInstanceOf[Import]) {
            primFunc.apply(args, this)
          }
          else {
            primFunc.apply(args, this, s)
          }
        } 
        else {
          throw new CompilerException("It's not a function", this.op)
        }
      }
    CallMemory.save(getSign(s), result)
    result
  }

  def typecheck(s: Scope): Value = {
    null
  }
  
  def codegen(s: Scope): org.jllvm.value.Value = {
    val opValue: Value = this.op.interp(s)
    if (opValue.isInstanceOf[Closure]) {
      val closure: Closure = opValue.asInstanceOf[Closure]
      val funcParams: Node = closure.func.params
      val params = if (funcParams.isInstanceOf[Tuple]) {
          funcParams.asInstanceOf[Tuple].elements.map { node => node.asInstanceOf[Name] }
        } else if (funcParams.isInstanceOf[Name]) {
          List(funcParams.asInstanceOf[Name])
        } else {
          throw new CompilerException("incorrent argument", this)
        }
      
      val func = this.op.codegen(s)
      val _params = params.zipWithIndex.map { case (param, i) => 
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
  
  def getSign(s: Scope):String = {
    if ("display".equals(op.toString()) || "newline".equals(op.toString())) {
      "noCache"
    }
    else {
      val argValues = Node.interpList(args.positional, s)
      op + argValues.toString()
    }
  }
  
  override
  def toString(): String = {
    op + args.toString()
  }
  
}

// memorize the value of the call for faster speed
object CallMemory {
  
  private val maxSize = 100
  
  private val memory = new LRUCache[String, Value](maxSize)
  
  def save(sign: String, value: Value): Unit = {
    if (!"noCache".equals(sign)) {
      memory.put(sign, value)
    }
  }
  
  def lookup(sign: String): Option[Value] = {
      memory.get(sign)
  }
  
}