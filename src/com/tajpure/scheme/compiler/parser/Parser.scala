package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.ast.Call
import com.tajpure.scheme.compiler.ast.Define
import com.tajpure.scheme.compiler.ast.If
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.ast.Func

object Parser extends App {
  
  @throws(classOf[ParserException])
  def parse(_path: String): Node = {
    val preParser: PreParser = new PreParser(_path)
    val preNode: Node = preParser.parse()
    parseNode(preNode)
  }
  
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
        val keyNode: Node = elements(0)
        
        if (keyNode.isInstanceOf[Name]) {
          keyNode.asInstanceOf[Name].id match {
            case Constants.DEFINE => parseDefine(tuple)
            case Constants.IF => parseIf(tuple)
            case Constants.LET => parseAssign(tuple)
            case Constants.LAMBDA => parseLambda(tuple)
            case default => parseCall(tuple)
          }
        } 
        else if (keyNode.isInstanceOf[Tuple]) {
          parseNode(keyNode)
        } 
        else {
          parseCall(tuple)
        }
      }
    }
  }
  
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
  
  def parseIf(tuple: Tuple): If = {
    null
  }
  
  def parseAssign(tuple: Tuple): Define = {
    null
  }
  
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
      var paramsName: List[Name] = List[Name]()
      params.foreach { node => 
        if (node.isInstanceOf[Name]) {
          paramsName = paramsName :+ node.asInstanceOf[Name]
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
  
  def parseCall(tuple: Tuple): Call = {
    null
  }
  
  println(parse("/home/taojx/sworkspace/SoScheme/test/hello.ss").interp(Scope.buildInitScope()))
}