package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.VoidList
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value

class Append extends PrimFunc("append" , -1) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size == 0) {
      new VoidList()
    }
    else {
      args.foldRight(new VoidList().asInstanceOf[Value])(
          (arg, tail) => {
            null            
          }
          )
    }
  }
  
  def pair2list(list: PairValue): List[Value] = {
    def rest(tail: Value): List[Value] = {
       if (tail.isInstanceOf[PairValue]) {
        val pair = tail.asInstanceOf[PairValue]
        List(pair.head).++(rest(pair.tail))
      }
      else {
        List(tail)
      }
    }
    List(list.head).+:(list.tail)
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    null
  }

  override
  def toString: String = {
    "append"
  }
}