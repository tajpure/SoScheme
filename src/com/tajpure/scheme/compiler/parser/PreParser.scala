package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.util.FileUtils
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Delimeter
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.util.Log

class PreParser(_path: String) {

  val file: String = FileUtils.unifyPath(_path)
  val lexer = new LexParser(_path)

  @throws(classOf[ParserException])
  def nextNode(): Node = {
    nextNode1(0)
  }
  
  @throws(classOf[ParserException])
  def nextNode1(depth: Int): Node = {
    val first: Node = lexer.nextToken()
    
    if (first == null) {
      null
    } else {
      if (Delimeter.isOpen(first)) {
        var elements: List[Node] = List[Node]()
        var next: Node = nextNode1(depth + 1)
        def loop() {
          if (!Delimeter._match(first, next)) {
            if (next == null) {
              throw new ParserException("unclosed delimeter till end of file: " + first.toString(), first)
            } else if (Delimeter.isClose(next)) {
              throw new ParserException("unmatched closing delimeter: " +
                  next.toString() + " does not close " + first.toString(), next)
            } else {
              elements = elements :+ next
              next = nextNode1(depth + 1)
              loop()
            }
          }
        }
        loop()
        new Tuple(elements, first, next, first.file, first.start, next.end, first.row, first.col)
      } else if (depth == 0 && Delimeter.isClose(first)) {
        throw new ParserException("unmatched closing delimeter: " + first.toString() +
            " does not close any open delimeter", first);
      } else {
        first
      }
      
    }
  }
  
  @throws(classOf[ParserException])
  def parse(): Node = {
    var elements: List[Node] = List[Node]()
    var s: Node = nextNode()
    val first: Node = s
    var last: Node = null
    def loop() {
      if (s != null) {
        elements = elements :+ s
        last = s
        s = nextNode()
        loop()
      }
    }
    loop()
    new Tuple(elements, Name.genName(Constants.PAREN_BEGIN), Name.genName(Constants.PAREN_END), file, first.start, last.end, 0, 0)
  }
}

object PreParser extends App {
  val preParser: PreParser = new PreParser("D:/workspace/workspace11/SoScheme/test/location.ss")
  try {
    Log.info("preparser result: " + preParser.parse().toString())
  } catch {
    case pe: ParserException => Log.error(pe.toString())
    case e: Exception => Log.error(e.toString())
  }
}