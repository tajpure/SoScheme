package com.tajpure.scheme.compiler.value.premitives

import org.jllvm.value.user.instruction.AddInstruction
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.value.FloatValue
import com.tajpure.scheme.compiler.value.FractionValue
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.value.Value
import org.jllvm.value.user.constant.ConstantInteger

class Add extends PrimFunc("+", -1) {

  def apply(args: List[Value], location: Node): Value = {
    if (args.size == 0) {
      throw new CompilerException("Exception: incorrect arguments count in call '+'", location)
    } 
    else if (args.size == 1) {
      args(0)
    }
    else {
      args.foldLeft(new IntValue(0).asInstanceOf[Value])((result, arg) => {
        if (result.isInstanceOf[IntValue] && arg.isInstanceOf[IntValue]) {
          new IntValue(result.asInstanceOf[IntValue].value + arg.asInstanceOf[IntValue].value)
        } 
        else if (result.isInstanceOf[IntValue] && arg.isInstanceOf[FloatValue]) {
          new FloatValue(result.asInstanceOf[IntValue].value + arg.asInstanceOf[FloatValue].value)
        } 
        else if (result.isInstanceOf[FloatValue] && arg.isInstanceOf[IntValue]) {
          new FloatValue(result.asInstanceOf[FloatValue].value + arg.asInstanceOf[IntValue].value)
        } 
        else if (result.isInstanceOf[FloatValue] && arg.isInstanceOf[FloatValue]) {
          new FloatValue(result.asInstanceOf[FloatValue].value + arg.asInstanceOf[FloatValue].value)
        } 
        else if (result.isInstanceOf[FractionValue] && arg.isInstanceOf[IntValue]) {
          val fraction: FractionValue = result.asInstanceOf[FractionValue]
          val value: Int =  arg.asInstanceOf[IntValue].value
          new FractionValue(fraction.numerator + value * fraction.denominator, fraction.denominator)
        }
        else if (result.isInstanceOf[FractionValue] && arg.isInstanceOf[FloatValue]) {
          val fraction: FractionValue = result.asInstanceOf[FractionValue]
          new FloatValue((fraction.numerator / fraction.denominator.toFloat) + arg.asInstanceOf[FloatValue].value)
        }
        else if (arg.isInstanceOf[FractionValue]) {
          val fraction: FractionValue = arg.asInstanceOf[FractionValue]
          if (result.isInstanceOf[IntValue]) {
            new FractionValue(fraction.numerator + result.asInstanceOf[IntValue].value * fraction.denominator, fraction.denominator)
          } 
          else if (result.isInstanceOf[FloatValue]) {
            new FloatValue((fraction.numerator / fraction.denominator.toFloat) + result.asInstanceOf[FloatValue].value)
          }
          else if (result.isInstanceOf[FractionValue]) {
            val fraction0: FractionValue = result.asInstanceOf[FractionValue]
            new FractionValue(fraction.numerator * fraction0.denominator + fraction.denominator * fraction0.numerator, fraction.denominator * fraction0.denominator)
          } 
          else {
            Log.error(location, "Exception: incorrect arguments in call '+' : " + arg)
            Value.VOID
          }
        }
        else {
          Log.error(location, "Exception: incorrect arguments in call '+' : " + arg)
          Value.VOID
        }
      })
    }
  }

  def typecheck(args: List[Value], location: Node): Value = {
    null
  }
  
  def codegen(args: List[Value], location: Node): Value = {
    null
  }
  
  override
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    val addInstruction: AddInstruction = new AddInstruction(s.codegen.builder, ConstantInteger.constI32(1),ConstantInteger.constI32(2),false, "d")
    null
  }

  override def toString: String = {
    "+"
  }

}