package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PairValue
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.VoidList
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.ConstValue

class Append extends PrimFunc("append" , -1) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size == 0) {
      new VoidList()
    }
    else {
      val list: List[Value] = args.foldRight(List[Value]())(
            (arg, list) => {
              if (arg.isInstanceOf[PairValue]) {
                list.:::(arg.asInstanceOf[PairValue].toList()) 
              }
              else if (arg.isInstanceOf[ConstValue]) {
                val constValue = arg.asInstanceOf[ConstValue].value
                if (constValue.isInstanceOf[PairValue]) {
                  list.:::(constValue.asInstanceOf[PairValue].toList()) 
                }
                else {
                  list.::(constValue)
                }
              }
              else {
                list.::(arg)
              }        
            }
            )
      val _list = list.filter { value => !value.isInstanceOf[VoidList] }
      new ListFunc().apply(_list, location)
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
    "append"
  }
}