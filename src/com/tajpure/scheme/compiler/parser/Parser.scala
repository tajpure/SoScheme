package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Call
import com.tajpure.scheme.compiler.ast.Define
import com.tajpure.scheme.compiler.ast.Func
import com.tajpure.scheme.compiler.ast.If
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Symbol
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.ast.Argument

object Parser extends App {
  
  @throws(classOf[ParserException])
  def parse(_path: String): Node = {
    val preParser: PreParser = new PreParser(_path)
    val preNode: Node = preParser.parse()
    parseNode(preNode)
  }
  
  @throws(classOf[ParserException])
  def parse(_source:String, _path: String): Node = {
    val preParser: PreParser = new PreParser(_source, _path)
    val preNode: Node = preParser.parse()
    parseNode(preNode)
  }
  
  @throws(classOf[ParserException])
  def parseNode(preNode: Node): Node = {
    if (!preNode.isInstanceOf[Tuple]) {
      preNode
    }
    else {
      val tuple: Tuple = preNode.asInstanceOf[Tuple]
      var elements: List[Node] = tuple.elements
      
      if (elements.isEmpty) {
        throw new ParserException("syntax error: ", tuple)
      } 
      else {
        val curNode: Node = elements(0)
        
        if (curNode.isInstanceOf[Symbol]) {
          curNode.asInstanceOf[Symbol].id match {
            case Constants.DEFINE => parseDefine(tuple)
            case Constants.IF => parseIf(tuple)
            case Constants.LET => parseAssign(tuple)
            case Constants.LAMBDA => parseLambda(tuple)
            case default => parseCall(tuple)
          }
        }
        else if (curNode.isInstanceOf[Tuple]) {
          parseNode(curNode)
        }
        else {
          parseCall(tuple)
        }
      }
    }
  }
  
  @throws(classOf[ParserException])
  def parseDefine(tuple: Tuple): Define = {
    var elements: List[Node] = tuple.elements
    if (elements.size != 3) {
       throw new ParserException("incorrect format of definition", tuple);
    }
    else {
      val pattern: Node = parseNode(elements(1))
      val value: Node = parseNode(elements(2))
      new Define(pattern, value, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
    }
  }
  
  @throws(classOf[ParserException])
  def parseIf(tuple: Tuple): If = {
    null
  }
  
  @throws(classOf[ParserException])
  def parseAssign(tuple: Tuple): Define = {
    null
  }
  
  @throws(classOf[ParserException])
  def parseLambda(tuple: Tuple): Func = {
    var elements: List[Node] = tuple.elements
    val preNode: Node = elements(1)
    if (elements.size != 3) {
       throw new ParserException("incorrect format of function", tuple)
    }
    else if (!preNode.isInstanceOf[Tuple]) {
       throw new ParserException("incorrect format of parameters:" + preNode.toString(), preNode)
    }
    else {
      val params: List[Node] = preNode.asInstanceOf[Tuple].elements
      var paramsName: List[Symbol] = List[Symbol]()
      params.foreach { node => 
        if (node.isInstanceOf[Symbol]) {
          paramsName = paramsName :+ node.asInstanceOf[Symbol]
        } 
        else {
          throw new ParserException("can't pass as an argument:" + node.toString(), node)
        }
      }
      
      val value: Node = parseNode(elements(2))
      val properties: Scope = null
      
      new Func(paramsName, properties, value, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
    }
  }
  
  @throws(classOf[ParserException])
  def parseCall(tuple: Tuple): Call = {
    val elements: List[Node] = tuple.elements
    val func: Node = parseNode(tuple.elements(0))
    val parsedArgs: List[Node] = parseList(elements)
    val argument: Argument = new Argument(parsedArgs)
    new Call(func, argument, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
  }
  
  @throws(classOf[ParserException])
  def parseList(preNodes: List[Node]): List[Node] = {
    preNodes.map { node => parseNode(node) }
  }
  
  println(parse("D:/workspace/workspace11/SoScheme/test/location.ss").interp(Scope.buildInitScope()))
  
}