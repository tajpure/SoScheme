package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.ast.If
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.ast.Define
import com.tajpure.scheme.compiler.ast.Call

class Parser() {
  
  @throws(classOf[ParserException])
  def parse(_path: String): Node = {
    val preParser: PreParser = new PreParser(_path)
    val preNode: Node = preParser.parse()
    parseNode(preNode)
  }
  
  def parseNode(preNode: Node): Node = {
    if (!preNode.isInstanceOf[Tuple]) {
      preNode
    } else {
      val tuple: Tuple = preNode.asInstanceOf[Tuple]
      var elements: List[Node] = tuple.elements
      
      if (elements.isEmpty) {
        throw new ParserException("syntax error: ", tuple)
      } else {
        val keyNode: Node = elements(0)
        
        if (keyNode.isInstanceOf[Name]) {
          keyNode.asInstanceOf[Name].id match {
            case Constants.IF => parseIf(tuple)
            case Constants.DEFINE => parseDefine(tuple)
            case Constants.LET => parseAssign(tuple)
            case Constants.LAMBDA => parseFun(tuple)
          }
        } else {
          parseCall(tuple)
        }
      }
    }
  }
  
  def parseIf(tuple: Tuple): If = {
    null
  }
  
  def parseDefine(tuple: Tuple): Define = {
    null
  }
  
  def parseAssign(tuple: Tuple): Define = {
    null
  }
  
  def parseFun(tuple: Tuple): Define = {
    null
  }
  
  def parseCall(tuple: Tuple): Call = {
    null
  }
}