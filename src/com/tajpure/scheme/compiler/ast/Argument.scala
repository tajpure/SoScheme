package com.tajpure.scheme.compiler.ast

class Argument(_elements: List[Node]) {

  val elements: List[Node] = _elements

  val positional: List[Node] = elements.map {
    node => {
        node
      }
  }
  
  override
  def toString(): String = {
    elements.foldLeft(" ( ")((node, str) => node + str + " ") + ")"
  }

}