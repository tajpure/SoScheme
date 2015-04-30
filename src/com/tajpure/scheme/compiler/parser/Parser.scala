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
import com.tajpure.scheme.compiler.ast.Block
import com.tajpure.scheme.compiler.exception.ParserException
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.IntNum
import com.tajpure.scheme.compiler.ast.Str
import com.tajpure.scheme.compiler.ast.FloatNum
import com.tajpure.scheme.compiler.ast.CharNum
import com.tajpure.scheme.compiler.ast.Bool
import com.tajpure.scheme.compiler.value.IntValue
import com.tajpure.scheme.compiler.ast._List
import com.tajpure.scheme.compiler.ast.PrimNode

object Parser extends App {

  @throws(classOf[ParserException])
  def parse(_path: String): Node = {
    val preParser: PreParser = new PreParser(_path)
    val preNode: Node = preParser.parse()
    parseNode(preNode)
  }

  @throws(classOf[ParserException])
  def parse(_source: String, _path: String): Node = {
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
      val elements: List[Node] = tuple.elements
      if (elements.isEmpty) {
        throw new ParserException("syntax error: ", tuple)
      } 
      else {
        val curNode: Node = elements(0)
        if (curNode.isInstanceOf[Name]) {
          curNode.asInstanceOf[Name].id match {
            case Constants.DEFINE => parseDefine(tuple)
            case Constants.IF => parseIf(tuple)
            case Constants.LET => parseAssign(tuple)
            case Constants.LAMBDA => parseLambda(tuple)
            case Constants.SEQ => parseBlock(tuple)
            case default => parseCall(tuple)
          }
        }
        else if (curNode.isInstanceOf[Symbol]) {
          curNode
        }
        else if (curNode.isInstanceOf[PrimNode]) {
          val intList = parseList(elements)
          new _List(intList, tuple)
        }
        else {
          parseCall(tuple)
        }
      }
    }
  }
  
  @throws(classOf[ParserException])
  def parseBlock(tuple: Tuple): Block = {
    val elements: List[Node] = tuple.elements
    val statements = parseList(elements.slice(1, elements.size))
    new Block(statements, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
  }

  @throws(classOf[ParserException])
  def parseDefine(tuple: Tuple): Define = {
    val elements: List[Node] = tuple.elements
    if (elements.size != 3) {
      throw new ParserException("incorrect format of definition", tuple)
    } 
    else {
      val pattern: Node = parseNode(elements(1))
      val value: Node = parseNode(elements(2))
      new Define(pattern, value, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
    }
  }

  @throws(classOf[ParserException])
  def parseIf(tuple: Tuple): If = {
    val elements: List[Node] = tuple.elements

    if (elements.size < 3) {
      throw new ParserException("incorrect format of function", tuple)
    }
    
    val test: Node = parseNode(elements(1))
    val then: Node = parseNode(elements(2))
    val _else: Node = if (elements.size == 4) {
        parseNode(elements(3))
      } else {
        null
      }
    
    new If(test, then, _else, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
  }

  @throws(classOf[ParserException])
  def parseAssign(tuple: Tuple): Define = {
    null
  }

  @throws(classOf[ParserException])
  def parseLambda(tuple: Tuple): Func = {
    val elements: List[Node] = tuple.elements

    if (elements.size < 3) {
      throw new ParserException("incorrect format of function", tuple)
    }

    val preNode: Node = elements(1)
    if (!preNode.isInstanceOf[Tuple]) {
      throw new ParserException("incorrect format of parameters:" + preNode.toString(), preNode)
    }

    val params: List[Node] = preNode.asInstanceOf[Tuple].elements
    val paramsName: List[Name] = params.map { node =>
      if (!node.isInstanceOf[Name]) {
        throw new ParserException("can't pass as an argument:" + node.toString(), node)
      }
      node.asInstanceOf[Name]
    }

    val statements: List[Node] = parseList(elements.slice(2, elements.size))
    val start: Int = statements(0).start
    val end: Int = statements(statements.size - 1).end
    val body: Block = new Block(statements, tuple.file, start, end, tuple.row, tuple.col)

    val properties: Scope = null
    new Func(paramsName, properties, body, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
  }

  @throws(classOf[ParserException])
  def parseCall(tuple: Tuple): Call = {
    val elements: List[Node] = tuple.elements
    val func: Node = parseNode(tuple.elements(0))
    val parsedArgs: List[Node] = parseList(elements.slice(1, elements.size))
    val argument: Argument = new Argument(parsedArgs)
    new Call(func, argument, tuple.file, tuple.start, tuple.end, tuple.row, tuple.col)
  }

  @throws(classOf[ParserException])
  def parseList(preNodes: List[Node]): List[Node] = {
    preNodes.map { node => parseNode(node) }
  }

  parse("D:/workspace/workspace11/SoScheme/test/helloworld.scm").interp(Scope.buildInitScope())

}