package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.exception.RunTimeException
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Closure
import com.tajpure.scheme.compiler.ast.Call
import com.tajpure.scheme.compiler.ast.Argument
import com.tajpure.scheme.compiler.value.VoidList
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.value.BoolValue

class FilterFunc extends PrimFunc("filter" , 2) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size != arity) {
      throw new CompilerException("args don't match the 'filter' function", location)
    }
    
    if (!args(0).isInstanceOf[Closure]) {
      throw new RunTimeException("args type error in function 'filter'", location)
    }
    else {
      val closure = args(0).asInstanceOf[Closure]
      val argList = if (args(1).isInstanceOf[PairValue]) {
          args(1).asInstanceOf[PairValue].toList()
        } else if (args(1).isInstanceOf[VoidList]) {
            List(args(1))
        } else {
          throw new RunTimeException(args(1) + " is not a proper list", location)
        }
      
      if (argList.size == 1 && argList(0).isInstanceOf[VoidList]) {
        argList(0)
      }
      else {
        val funcScope: Scope = new Scope(closure.env)
        val funcParams: Node = closure.func.params
        
        if (closure.properties != null) {
          Scope.mergeDefault(closure.properties, funcScope)
        }
        
        val list = argList.filter { arg => {
              if (funcParams.isInstanceOf[Tuple]) {
                val elements = funcParams.asInstanceOf[Tuple].elements
                if (elements.size != 1) {
                  throw new RunTimeException("incorrect arguments count", location)
                }
                else {
                  if (elements(0).isInstanceOf[Name]) {
                    funcScope.putValue(elements(0).asInstanceOf[Name].id, arg)
                  }
                }
              }
              val result = closure.func.body.interp(funcScope)
              if (result.isInstanceOf[BoolValue]) {
                result.asInstanceOf[BoolValue].value
              }
              else {
                true
              }
            }}
        
        new ListFunc().apply(list, location)
      }
    }
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    null
  }

  override
  def toString: String = {
    "filter"
  }
}