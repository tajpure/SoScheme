package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Str
import com.tajpure.scheme.compiler.parser.ParserException
import com.tajpure.scheme.compiler.util.FileUtils
import com.tajpure.scheme.compiler.ast.IntNum
import com.tajpure.scheme.compiler.ast.FloatNum
import com.tajpure.scheme.compiler.ast.Keyword
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.Delimeter

/**
 * Split source file
 */
class Lexer(path: String) {

  var offset: Int = -1
  var row: Int = -1
  var col: Int = -1

  val source: String = FileUtils.readFile(path)
  val file: String = FileUtils.unifyPath(path)

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
    }
    found
  }

  def skipSpacesAndComments() {
    if (skipSpaces() || skipComments()) {
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
        throw new ParserException("runaway string", startRow, startCol, offset);
      } else if (source.startsWith(Constants.STRING_END, offset)) {
        skip(Constants.STRING_END.length());
      } else {
        forward()
        scanChar()
      }
    }

    scanChar()
    val end: Int = offset
    val content: String = source.substring(start + Constants.STRING_BEGIN.length(), end + Constants.STRING_END.length())
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
        throw new ParserException("runaway string", startRow, startCol, offset);
      } else if (isNumberOrChar(source.charAt(offset))) {
        if (source.charAt(offset) == '.') {
          isInt = false
        }
      } else {
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

    skipSpacesAndComments()

    if (offset >= source.length()) {
      null
    }

    val cur = source.charAt(offset)
    if (Delimeter.isDelimiter(cur)) {
      val ret: Node = new Delimeter(Character.toString(cur), file, offset, offset + 1, row, col)
      forward()
      ret
    }

    if (source.startsWith(Constants.STRING_BEGIN, offset)) {
      scanString()
    }

    if (Character.isDigit(source.charAt(offset)) ||
      ((source.charAt(offset) == '+' || source.charAt(offset) == '-') 
      && offset + 1 < source.length() && Character.isDigit(source.charAt(offset + 1)))) {
      scanNumber()
    }
    
    if (isIdentifierChar(source.charAt(offset))) {
      scanNameOrKeyword()
    }
    
    throw new ParserException("unrecognized syntax: " + source.substring(offset, offset + 1),
                row, col, offset)
  }
}