package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.ast.Delimeter
import com.tajpure.scheme.compiler.ast.FloatNum
import com.tajpure.scheme.compiler.ast.IntNum
import com.tajpure.scheme.compiler.ast.Keyword
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Str
import com.tajpure.scheme.compiler.util.FileUtils
import com.tajpure.scheme.compiler.util.Log

/**
 * Split source file
 */
class LexParser(_path: String) {

  var offset: Int = 0
  var row: Int = 0
  var col: Int = 0

  val source: String = FileUtils.readFile(_path)
  val file: String = FileUtils.unifyPath(_path)

  if (source == null) {
    Log.error("Failed to read the file:" + file)
  }
  
  Delimeter.addDelimiterPair(Constants.PAREN_BEGIN, Constants.PAREN_END)
  
  def forward() {
    if (source.charAt(offset) != '\n') {
      col += 1
    } else {
      row += 1
      col = 0
    }
    offset += 1
  }

  def skip(n: Int) {
    (1 to n).foreach(_ => forward())
  }

  def skipSpaces(): Boolean = {
    var found = false
    if (source.charAt(offset) == ' ') {
      found = true
      skip(1)
      skipSpaces()
    }
    found
  }

  def skipComments(): Boolean = {
    var found = false
    if (source.startsWith(Constants.COMMENTS, offset)) {
      found = true
      while (offset < source.length && source.charAt(offset) != '\n') {
        skip(1)
      }
      if (offset < source.length) {
         forward();
      }
    }
    found
  }
  
  def skipEnter(): Boolean = {
    var found = false
    if (source.charAt(offset) == '\r' || source.charAt(offset) == '\n') {
      found = true
      forward()
    }
    found
  }

  def skipSpacesAndComments() {
    if (skipSpaces() || skipComments() || skipEnter()) {
      skipSpacesAndComments()
    }
  }

  def scanString(): Node = {
    val start: Int = offset
    val startRow: Int = row
    val startCol: Int = col
    skip(Constants.STRING_BEGIN.length());

    def scanChar() {
      if (offset >= source.length() || source.charAt(offset) == '\n') {
        throw new ParserException("string format error:", startRow, startCol, offset);
      } else if (source.startsWith(Constants.STRING_END, offset)) {
        skip(Constants.STRING_END.length());
      } else {
        forward()
        scanChar()
      }
    }

    scanChar()
    val end: Int = offset
    val content: String = source.substring(start + Constants.STRING_BEGIN.length(), end - Constants.STRING_END.length())
    new Str(content, file, start, end, row, col)
  }

  def isNumberOrChar(ch: Char): Boolean = {
    Character.isLetterOrDigit(ch) || ch == '.' || ch == '+' || ch == '-'
  }

  def scanNumber(): Node = {
    val start: Int = offset
    val startRow: Int = row
    val startCol: Int = col
    var isInt: Boolean = true

    def scanNum() {
      if (offset >= source.length() || source.charAt(offset) == '\n') {
        throw new ParserException("number format error:", startRow, startCol, offset);
      } else if (isNumberOrChar(source.charAt(offset))) {
          if (source.charAt(offset) == '.') {
            isInt = false
          }
        forward()
        scanNum()
      }
    }

    scanNum()
    val end: Int = offset
    val content: String = source.substring(start, end)
    if (isInt) {
      new IntNum(content, file, start, end, startRow, startCol)
    } else {
      new FloatNum(content, file, start, end, startRow, startCol)
    }
  }

  def isIdentifierChar(ch: Char): Boolean = {
    Character.isLetterOrDigit(ch)
  }

  def scanNameOrKeyword(): Node = {
    val start: Int = offset
    val startRow: Int = row
    val startCol: Int = col

    def scanIdent() {
      if (offset < source.length && isIdentifierChar(source.charAt(offset))) {
        forward()
        scanIdent()
      }
    }
    scanIdent()

    val content = source.substring(start, offset)
    if (Constants.keyword.contains(content)) {
      new Keyword(content, file, start, offset, startRow, startCol)
    } else {
      new Name(content, file, start, offset, startRow, startCol)
    }
  }

  @throws(classOf[ParserException])
  def nextToken(): Node = {

    if (offset >= source.length()) {
      null
    } 
    
    else {
      skipSpacesAndComments()

      val cur = source.charAt(offset)
      if (Delimeter.isDelimiter(cur)) {
        val ret: Node = new Delimeter(Character.toString(cur), file, offset, offset + 1, row, col)
        forward()
        ret
      } 
      
      else if (source.startsWith(Constants.STRING_BEGIN, offset)) {
        scanString()
      } 
      
      else if (Character.isDigit(source.charAt(offset)) ||
        ((source.charAt(offset) == '+' || source.charAt(offset) == '-') 
        && offset + 1 < source.length() && Character.isDigit(source.charAt(offset + 1)))) {
        scanNumber()
      } 
      
      else if (isIdentifierChar(source.charAt(offset))) {
        scanNameOrKeyword()
      }
      
      else {
        throw new ParserException("unrecognized syntax: " + source.substring(offset, offset + 1),
                  row, col, offset)
      }
    }
  }
}

object LexParser extends App {
  val lexer: LexParser = new LexParser("D:/workspace/workspace11/SoScheme/test/hello.ss")
  var tokens: List[Node] = List[Node]()
  var n:Node = lexer.nextToken()
  
  def parse() {
    if (n != null) {
      tokens  = tokens :+ n
      try {
        n = lexer.nextToken()
        parse()
      } catch {
        case pe: ParserException => Log.error(pe.toString())
        case e: Exception => Log.error(e.toString())
      }
    }
  }
  
  parse()
  
  Log.info("LexParser result:")
  tokens.foreach { node => Log.info(node.toString()) }
}