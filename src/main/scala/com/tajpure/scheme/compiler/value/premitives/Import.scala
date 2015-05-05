package com.tajpure.scheme.compiler.value.premitives

import com.tajpure.scheme.compiler.value.PrimFunc
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.exception.CompilerException
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.StringValue
import com.tajpure.scheme.compiler.parser.Parser
import com.tajpure.scheme.compiler.util.FileUtils

class Import extends PrimFunc("import", 1) {
  
  def apply(args: List[Value], location: Node): Value = {
    Value.VOID
  }
  
  override
  def apply(args: List[Value], location: Node, s: Scope): Value = {
    if (args.size != arity) {
      throw new CompilerException("args don't match the 'import' function", location)
    }
    else {
      if (args(0).isInstanceOf[StringValue]) {
        val path = args(0).asInstanceOf[StringValue].value
        val source = FileUtils.read(path)
        Parser.parse(source, "import").interp(s)
        s.parent.parent.setInnerScope(s.innerScope)
        Value.VOID
      }
      else {
        throw new CompilerException("incorrect arguments in call 'import'", location)
      }
    }
  }
  
  def typecheck(args: List[Value], location: Node): Value= {
    // TODO
    null
  }
  
  def codegen(args: List[org.jllvm.value.Value], location: Node, s: Scope): org.jllvm.value.Value = {
    // TODO
    null
  }

  override
  def toString: String = {
    "import"
  }
  
}