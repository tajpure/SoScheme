package com.tajpure.scheme.compiler.visitor

import com.tajpure.scheme.compiler.ast.Define
import com.tajpure.scheme.compiler.ast.Func
import com.tajpure.scheme.compiler.ast.IntNum
import com.tajpure.scheme.compiler.ast.FloatNum
import com.tajpure.scheme.compiler.ast.Bool
import com.tajpure.scheme.compiler.ast.Block
import com.tajpure.scheme.compiler.ast.Call

trait Visitor {
  
  def visit(node: Define): Unit
  
  def visit(node: Func): Unit
  
  def visit(node: Symbol): Unit
  
  def visit(node: IntNum): Unit
  
  def visit(node: FloatNum): Unit
  
  def visit(node: Call): Unit
  
  def visit(node: Bool): Unit
  
  def visit(node: Block): Unit
  
}