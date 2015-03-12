package com.tajpure.scheme.compiler.ast

import com.tajpure.scheme.compiler.Scope
import com.tajpure.scheme.compiler.value.Value
import scala.collection.mutable.HashSet
import java.util.HashMap

object Delimeter {
  
  val delims: HashSet[String] = new HashSet[String]

  val delimMap: HashMap[String, String] = new HashMap[String, String]

  def addDelimiterPair(open: String, close: String) {
    delims.add(open)
    delims.add(close)
    delimMap.put(open, close)
  }

  def isDelimiter(c: Char): Boolean = {
    delims.contains(Character.toString(c))
  }

  def isOpen(c: Node): Boolean = {
    (c.isInstanceOf[Delimeter]) && delimMap.keySet().contains(c.asInstanceOf[Delimeter].shape)
  }

  def isClose(c: Node): Boolean = {
    (c.isInstanceOf[Delimeter]) && delimMap.values().contains(c.asInstanceOf[Delimeter].shape)
  }

  def _match(open: Node, close: Node): Boolean = {
    if (!(open.isInstanceOf[Delimeter]) || !(close.isInstanceOf[Delimeter])) {
      false
    }
    val matched: String = delimMap.get(open.asInstanceOf[Delimeter].shape)
    matched != null && matched.equals(close.asInstanceOf[Delimeter].shape)
  }
}

class Delimeter(_shape: String, _file: String, _start: Int, _end: Int,
                _row: Int, _col: Int) extends Node(_file, _start, _end, _row, _col) {

  val shape = _shape

  def interp(s: Scope): Value = {
    null
  }

  override
  def typeCheck(s: Scope): Value = {
    null
  }
}