package com.tajpure.scheme.compiler.value

import com.tajpure.scheme.compiler.ast.Func
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Node

class Closure(_func: Func, _properties: Scope, _env: Scope) extends Value {
  
  val func: Func = _func
  
  val properties: Scope = _properties
  
  val env: Scope = _env
  
  override
  def toString(): String = {
    func.toString()
  }
  
}