package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Argument
import com.tajpure.scheme.compiler.ast.Block
import com.tajpure.scheme.compiler.ast.Call
import com.tajpure.scheme.compiler.ast.Define
import com.tajpure.scheme.compiler.ast.Func
import com.tajpure.scheme.compiler.ast.If
import com.tajpure.scheme.compiler.ast.Let
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Symbol
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.exception.ParserException

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
          throw new ParserException("syntax error", tuple)
      } 
      else {
        val curNode: Node = elements(0)
        if (curNode.isInstanceOf[Name]) {
          curNode.asInstanceOf[Name].id match {
            case Constants.DEFINE => parseDefine(tuple)
            case Constants.IF => parseIf(tuple)
            case Constants.LET => parseLet(tuple)
            case Constants.LAMBDA => parseLambda(tuple)
            case Constants.BEGIN => parseBlock(tuple)
            case default => parseCall(tuple)
          }
        }
        else {
          parseCall(tuple)
        }
      }
    }
  }
  
  @throws(classOf[ParserException])
  def parseBlock(tuple: Tuple): Node = {
    val elements: List[Node] = tuple.elements
    val statements = parseList(elements.slice(1, elements.size))
    new Block(statements, tuple)
  }

  @throws(classOf[ParserException])
  def parseDefine(tuple: Tuple): Node = {
    val elements: List[Node] = tuple.elements
    if (elements.size < 3) {
      throw new ParserException("incorrect format of definition", tuple)
    } 
    else {
      if (elements(1).isInstanceOf[Tuple]) {
          val funcTuple = elements(1).asInstanceOf[Tuple]
          val funcElements = funcTuple.elements
          val pattern: Node = parseNode(funcElements(0))
          val paramsTuple = new Tuple(funcElements.slice(1, funcElements.size), funcTuple)
          val lambdaElements = elements.slice(2, elements.size).:::(List(Name.genName(Constants.LAMBDA), paramsTuple))
          val lambdaTuple = new Tuple(lambdaElements, funcTuple)
          val value: Node = parseNode(lambdaTuple)
          new Define(pattern, value, tuple)
        } else {
          val pattern: Node = parseNode(elements(1))
          val value: Node = parseNode(elements(2))
          new Define(pattern, value, tuple)
        }
    }
  }

  @throws(classOf[ParserException])
  def parseIf(tuple: Tuple): If = {
    val elements: List[Node] = tuple.elements

    if (elements.size < 3 || elements.size > 4) {
      throw new ParserException("incorrect format of if", tuple)
    }
    
    val test: Node = parseNode(elements(1))
    val then: Node = parseNode(elements(2))
    val _else: Node = if (elements.size == 4) {
        parseNode(elements(3))
      } else {
        null
      }
    
    new If(test, then, _else, tuple)
  }

  @throws(classOf[ParserException])
  def parseLet(tuple: Tuple): Node = {
    val elements = tuple.elements
    
    if (elements.size < 3) {
      throw new ParserException("incorrect format of let", tuple)
    }
    
    val bindings = 
      if (elements(1).isInstanceOf[Tuple]) {
        parseBindings(elements(1).asInstanceOf[Tuple])
      } else {
        throw new ParserException("incorrect format of bindings", tuple)
      }
    val statements = parseList(elements.slice(2, elements.size))
    val start: Int = statements(0).start
    val end: Int = statements(statements.size - 1).end
    val body: Block = new Block(statements, tuple.file, start, end, tuple.row, tuple.col)
    
    new Let(bindings, body, tuple)
  }

  @throws(classOf[ParserException])
  def parseBindings(tuple: Tuple): List[Node] = {
    val elements = tuple.elements
    elements.map { element => {
      if (element.isInstanceOf[Tuple]) {
        val origin = element.asInstanceOf[Tuple]
        val define = new Tuple(Name.genName(Constants.DEFINE)::origin.elements, origin.open, origin.close, origin)
        parseDefine(define)
      }
      else {
        throw new ParserException("incorrect format of bindings", tuple)
      }
    } }
  }

  @throws(classOf[ParserException])
  def parseLambda(tuple: Tuple): Node = {
    val elements: List[Node] = tuple.elements

    if (elements.size < 3) {
      throw new ParserException("incorrect format of function", tuple)
    }

    val params: Node = elements(1)
    
    // the type of the parameters must be "Name" or "Tuple"
    if (params.isInstanceOf[Tuple]) {
      params.asInstanceOf[Tuple].elements.map { node =>
      if (!node.isInstanceOf[Name]) {
        throw new ParserException("can't pass as an argument:" + node.toString(), node)
      }
      }
    } else {
      if (!params.isInstanceOf[Name]) {
        throw new ParserException("can't pass as an argument:" + params.toString(), params)
      }
    }

    val statements: List[Node] = parseList(elements.slice(2, elements.size))
    val start: Int = statements(0).start
    val end: Int = statements(statements.size - 1).end
    val body: Block = new Block(statements, tuple.file, start, end, tuple.row, tuple.col)

    val properties: Scope = null
    new Func(params, properties, body, tuple)
  }

  @throws(classOf[ParserException])
  def parseCall(tuple: Tuple): Node = {
    val elements: List[Node] = tuple.elements
    val func: Node = parseNode(tuple.elements(0))
    val parsedArgs: List[Node] = parseList(elements.slice(1, elements.size))
    val argument: Argument = new Argument(parsedArgs)
    new Call(func, argument, tuple)
  }

  @throws(classOf[ParserException])
  def parseList(preNodes: List[Node]): List[Node] = {
    preNodes.map { node => parseNode(node) }
  }

  parse("./src/test/resources/scheme/helloworld.scm").interp(Scope.buildInitScope())
  
  def parseSource(source: String): String = {
    parse(source, "/visual").toString()
  }

}