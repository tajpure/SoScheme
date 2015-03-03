package com.tajpure.scheme.compiler

import com.tajpure.scheme.compiler.parser.ParserException
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Str
import com.tajpure.scheme.compiler.util.FileUtils

/**
 * Split source file
 */
class Lexer(path: String) {

  var offset:Int = -1
  var row:Int = -1
  var col:Int = -1
  val source:String = FileUtils.readFile(path)
  val file:String = FileUtils.unifyPath(path)
  
  def forward() {
    if (source.charAt(offset) != '\n') {
      col += 1
    } else {
      row += 1
      col = 0
    }
    offset += 1
  }
  
  def skip(n:Int) {
    (1 to n).foreach(_ => forward())
  }
  
  def skipSpaces() {
    if (source.charAt(offset) == ' ') {
      skip(1)
      skipSpaces()
    }
  }
  
  def skipComments() {
    var found = false
    if (source.startsWith(Constants.COMMENTS)) {
      while (offset < source.length && source.charAt(offset) != '\n') {
        skip(1)
      }
    }
  }
  
  def scanString() : Node = {
    val start : Int = offset
    val startRow : Int = row
    val startCol : Int = col
    
    skip(Constants.STRING_BEGIN.length());
    
    def scanChar() {
     if (offset >= source.length() || source.charAt(offset) == '\n') {
         throw new ParserException("runaway string", startRow, startCol, offset);
     } 
     
     else if (source.startsWith(Constants.STRING_END, offset)) {
         skip(Constants.STRING_END.length());
     } 
     
     else {
       forward()
       scanChar()
     }
    }
    scanChar()
    val end:Int = offset
    val content:String = source.substring(start + Constants.STRING_BEGIN.length(), end + Constants.STRING_END.length())
    new Str(content, file, start, end, row, col)
  }
  
  def isNumberOrChar(ch: Char) : Boolean = {
    Character.isLetterOrDigit(ch) ||  ch == '.' || ch == '+' || ch == '-' 
  }
  
//  def scanNumber() : Node = {
//    
//  }
//  
//  def scanIent() : Node = {
//    
//  }
//  
//  def scanKeyword() : Node = {
//    
//  }
  
  @throws(classOf[ParserException])
  def nextToken() = {
    
  }
}