package com.tajpure.scheme.compiler.parser

import com.tajpure.scheme.compiler.Constants
import com.tajpure.scheme.compiler.ast.Delimeter
import com.tajpure.scheme.compiler.ast.FloatNum
import com.tajpure.scheme.compiler.ast.IntNum
import com.tajpure.scheme.compiler.ast.Keyword
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Quote
import com.tajpure.scheme.compiler.ast.Str
import com.tajpure.scheme.compiler.ast.Symbol
import com.tajpure.scheme.compiler.exception.ParserException
import com.tajpure.scheme.compiler.util.FileUtils
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.ast.Bool

/**
 * Split source file
 */
class LexParser(_source:String, _path: String) {

  def this(_path: String) {
    this(null, _path)
  }
  
  var offset: Int = 0
  
  var row: Int = 0
  
  var col: Int = 0

  val source: String = _source match {
    case null => FileUtils.readFile(_path)
    case "" => FileUtils.readFile(_path)
    case default => _source
  }
  
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
    (1 to n).foreach( _ => forward())
  }

  def skipSpaces(): Boolean = {
    if (offset < source.length && source.charAt(offset) == ' ') {
      skip(1)
      skipSpaces()
      true
    } 
    else {
      false  
    }
  }

  def skipComments(): Boolean = {
    if (source.startsWith(Constants.COMMENTS, offset)) {
      while (offset < source.length && source.charAt(offset) != '\n') {
        skip(1)
      }
      if (offset < source.length) {
         forward();
      }
      true
    }
    else {
      false
    }
  }
  
  def skipEnter(): Boolean = {
    if (offset < source.length && (source.charAt(offset) == '\r' || source.charAt(offset) == '\n')) {
      forward()
      true
    } else {
      false
    }
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

    def loop() {
      if (offset >= source.length() || source.charAt(offset) == '\n') {
        throw new ParserException("string format error:", startRow, startCol, offset);
      } 
      else if (source.startsWith(Constants.STRING_END, offset)) {
        skip(Constants.STRING_END.length());
      } 
      else {
        forward()
        loop()
      }
    }
    loop()
    
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

    def loop() {
      if (offset >= source.length() || source.charAt(offset) == '\n') {
        throw new ParserException("Exception: number format error:", startRow, startCol, offset);
      } 
      else if (isNumberOrChar(source.charAt(offset))) {
          if (source.charAt(offset) == '.') {
            isInt = false
          }
        forward()
        loop()
      }
    }
    loop()
    
    val end: Int = offset
    val content: String = source.substring(start, end)
    if (isInt) {
      new IntNum(content, file, start, end, startRow, startCol)
    } 
    else {
      new FloatNum(content, file, start, end, startRow, startCol)
    }
  }
  
  def scanQuote(): Node = {
    val start: Int = offset
    val startRow: Int = row
    val startCol: Int = col
    
    def loop() {
      if (offset < source.length && source.charAt(offset) == Constants.QUOTE) {
        forward()
        loop()
      }
    }
    loop()
    
    val end: Int = offset
    val content: String = source.substring(start, end)
    new Quote(content, file, start, end, row, col)
  }
  
  def isIdentifierChar(ch: Char): Boolean = {
    Character.isLetterOrDigit(ch) || Constants.IDENT_CHARS.contains(ch)
  }

  def scanSymbol(): Node = {
    val start: Int = offset
    val startRow: Int = row
    val startCol: Int = col

    def loop() {
      if (offset < source.length && isIdentifierChar(source.charAt(offset))) {
        forward()
        loop()
      }
    }
    loop()

    val content = source.substring(start, offset)
    new Symbol(content, file, start, offset, startRow, startCol)
  }

  @throws(classOf[ParserException])
  def nextToken(): Node = {
      
    skipSpacesAndComments()

    if (offset >= source.length()) {
      null
    } 
    else {
      val cur = source.charAt(offset)
      if (Delimeter.isDelimiter(cur)) {
        val ret: Node = new Delimeter(Character.toString(cur), file, offset, offset + 1, row, col)
        forward()
        ret
      } 
      else if (source.startsWith(Constants.STRING_BEGIN, offset)) {
        scanString()
      } 
      else if (source.charAt(offset) == Constants.QUOTE) {
        scanQuote()
      }
      else if (Character.isDigit(source.charAt(offset)) ||
        ((source.charAt(offset) == '+' || source.charAt(offset) == '-') 
        && offset + 1 < source.length() && Character.isDigit(source.charAt(offset + 1)))) {
        scanNumber()
      } 
      else if (isIdentifierChar(source.charAt(offset))) {
        scanSymbol()
      }
      else {
        throw new ParserException("unrecognized syntax: " + source.substring(offset, offset + 1),
                  row, col, offset)
      }
    }
  }
  
}

object LexParser extends App {
  
//  val lexer: LexParser = new LexParser("D:/workspace/workspace11/SoScheme/test/hello.scm")
  val lexer: LexParser = new LexParser("/home/taojx/sworkspace/SoScheme/test/location.scm")
  
  var tokens: List[Node] = List[Node]()
  
  var n: Node = lexer.nextToken()
  
  def loop() {
    if (n != null) {
      tokens  = tokens :+ n
      try {
        n = lexer.nextToken()
        loop()
      } 
      catch {
        case pe: ParserException => Log.error(pe.toString())
        case e: Exception => Log.error(e.toString())
      }
    }
  }
  loop()
  
  Log.info("LexParser result:")
  tokens.foreach { node => Log.info(node.toString()) }
  
}