package com.tajpure.scheme.compiler.ast

class Argument(val elements: List[Node]) {

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