package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.util.Log

class Add extends PrimFunc("+" , -1) {
  
  def apply(args: List[Value], location: Node): Value = {
//    var hasFloat = false
//    val sum = args.foldLeft(0)( (sum, value) => {
//      if (value.isInstanceOf[IntValue]) {
//        sum + value.asInstanceOf[IntValue].value
//      } else if (value.isInstanceOf[FloatValue]) {
//        hasFloat = true
//        sum + value.asInstanceOf[FloatValue].value
//      } else {
//        throw new Exception()
//      }
//    })
    val val1: Value = args(0)
    val val2: Value = args(1)
    
    if (val1.isInstanceOf[IntValue] && val2.isInstanceOf[IntValue]) {
      new IntValue(val1.asInstanceOf[IntValue].value + val2.asInstanceOf[IntValue].value)
    } else if (val1.isInstanceOf[FloatValue] && val2.isInstanceOf[IntValue]) {
      new FloatValue(val1.asInstanceOf[FloatValue].value + val2.asInstanceOf[IntValue].value)
    } else if (val1.isInstanceOf[IntValue] && val2.isInstanceOf[FloatValue]) {
      new FloatValue(val1.asInstanceOf[IntValue].value + val2.asInstanceOf[FloatValue].value)
    } else if (val1.isInstanceOf[FloatValue] && val2.isInstanceOf[FloatValue]) {
      new FloatValue(val1.asInstanceOf[FloatValue].value + val2.asInstanceOf[FloatValue].value)
    } else {
      Log.error(location, "+ args error")
      Value.VOID
    }
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    null
  }
  
  override
  def toString: String = {
    "+"
  }

}