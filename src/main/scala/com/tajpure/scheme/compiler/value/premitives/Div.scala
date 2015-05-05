package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.value.FractionValue
import com.tajpure.scheme.compiler.value.VoidValue
import com.tajpure.scheme.compiler.Scope

class Div extends PrimFunc("/", -1) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size == 0) {
      throw new CompilerException("incorrect arguments count in call '/'", location)
    } 
    else if (args.size == 1) {
       if (args(0).isInstanceOf[IntValue]) {
         new FractionValue( 1, args(0).asInstanceOf[IntValue].value)
      }
      else if (args(0).isInstanceOf[FloatValue]) {
         new FloatValue( 1 / args(0).asInstanceOf[FloatValue].value)
      }
      else {
         Log.error(location, "incorrect arguments in call '/'")
         Value.VOID
      }
    } 
    else {
      args.foldLeft(Value.VOID: Value)((result, arg) => {
        if (result.isInstanceOf[VoidValue]) {
          arg
        }
        else if (result.isInstanceOf[IntValue] && arg.isInstanceOf[IntValue] && arg.asInstanceOf[IntValue].value != 0) {
          new FractionValue(result.asInstanceOf[IntValue].value, arg.asInstanceOf[IntValue].value)
        }
        else if (result.isInstanceOf[FractionValue] && arg.isInstanceOf[IntValue] && arg.asInstanceOf[IntValue].value != 0) {
          val fraction: FractionValue = result.asInstanceOf[FractionValue]
          new FractionValue(fraction.numerator, fraction.denominator * arg.asInstanceOf[IntValue].value)
        }
        else if (result.isInstanceOf[FractionValue] && arg.isInstanceOf[IntValue] && arg.asInstanceOf[FloatValue].value != 0) {
          val fraction: FractionValue = result.asInstanceOf[FractionValue]
          new FloatValue(fraction.numerator / (fraction.denominator * arg.asInstanceOf[IntValue].value))
        }
        else if (result.isInstanceOf[IntValue] && arg.isInstanceOf[FloatValue] && arg.asInstanceOf[FloatValue].value != 0) {
          new FloatValue(result.asInstanceOf[IntValue].value / arg.asInstanceOf[FloatValue].value)
        } 
        else if (result.isInstanceOf[FloatValue] && arg.isInstanceOf[IntValue] && arg.asInstanceOf[IntValue].value != 0) {
          new FloatValue(result.asInstanceOf[FloatValue].value / arg.asInstanceOf[IntValue].value)
        } 
        else if (result.isInstanceOf[FloatValue] && arg.isInstanceOf[FloatValue] && arg.asInstanceOf[FloatValue].value != 0) {
          new FloatValue(result.asInstanceOf[FloatValue].value / arg.asInstanceOf[FloatValue].value)
        } 
        else {
          Log.error(location, "Exception: incorrect arguments in call '/'")
          Value.VOID
        }
      })
    }
  }

  def typecheck(args: List[Value], location: Node): Value = {
    null
  }
  
 def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    if (args.size != 2) {
      throw new CompilerException("incorrect arguments count in call '/'", location)
    }
    else if (args(0).isInstanceOf[org.jllvm.value.Value] && args(1).isInstanceOf[org.jllvm.value.Value]) {
      s.codegen.builder.buildFDiv(args(0), args(1), "div")
    }
    else {
      throw new CompilerException("incorrect arguments", location)
    }
  }

  override def toString: String = {
    "/"
  }

}