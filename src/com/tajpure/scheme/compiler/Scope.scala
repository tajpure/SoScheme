package com.tajpure.scheme.compiler

import scala.collection.mutable.HashMap
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.value.Type
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.premitives.Add
import com.tajpure.scheme.compiler.value.premitives.And
import com.tajpure.scheme.compiler.value.premitives.Div
import com.tajpure.scheme.compiler.value.premitives.Eq
import com.tajpure.scheme.compiler.value.premitives.GT
import com.tajpure.scheme.compiler.value.premitives.GTE
import com.tajpure.scheme.compiler.value.premitives.LT
import com.tajpure.scheme.compiler.value.premitives.LTE
import com.tajpure.scheme.compiler.value.premitives.Mult
import com.tajpure.scheme.compiler.value.premitives.Not
import com.tajpure.scheme.compiler.value.premitives.Or
import com.tajpure.scheme.compiler.value.premitives.Sub
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.util.Log

class Scope(_parent: Scope) {

  val map = new HashMap[String, HashMap[String, Object]]

  val parent: Scope = _parent

  def this() = this(null)

  def copy(): Scope = {
    val ret: Scope = new Scope
    map.keySet.foreach { name =>
      {
        ret.map.put(name, map.get(name).get)
      }
    }
    ret
  }

  def putAll(other: Scope): Unit = {
    other.map.keySet.foreach { name =>
      {
        map.put(name, other.map.get(name).get)
      }
    }
  }

  def lookup(name: String): Value = {
    val v: Object = lookupProperty(name, "value")
    if (v == null) {
      null
    } else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } else {
      null
    }
  }

  def lookUpLocal(name: String): Value = {
    val v: Object = lookupPropertyLocal(name, "value")
    if (v == null) {
      null
    } else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } else {
      null
    }
  }

  def lookupType(name: String): Value = {
    val v: Object = lookupProperty(name, "type")
    if (v == null) {
      null
    } else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } else {
      null
    }
  }

  def lookupLocalType(name: String): Value = {
    val v: Object = lookupPropertyLocal(name, "type")
    if (v == null) {
      null
    } else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } else {
      null
    }
  }

  def lookupPropertyLocal(name: String, key: String): Object = {
    val item = map.get(name)
    if (!item.isEmpty) {
      item.get(key)
    } else {
      null
    }
  }

  def lookupProperty(name: String, key: String): Object = {
    val v: Object = lookupPropertyLocal(name, key)
    if (v == null) {
      null
    } else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } else {
      null
    }
  }

  def lookupAllProps(name: String): HashMap[String, Object] = {
    map.get(name).get
  }

  def findDefiningScope(name: String): Scope = {
    val v: Object = map.get(name)
    if (v != null) {
      this
    } else if (parent != null) {
      parent.findDefiningScope(name)
    } else {
      null
    }
  }
  
  def put(name: String, key: String, value: Object): Unit = {
    var item: HashMap[String, Object] = null
    if (map.get(name).isEmpty) {
      item = new HashMap[String, Object]()
    } else {
      item = map.get(name).get
    }
    item.put(key, value)
    map.put(name, item)
  }
  
  def putProperties(name: String, properties: HashMap[String, Object]): Unit = {
    var item: HashMap[String, Object] = map.get(name).get
    if (item == null) {
      item = new HashMap[String, Object]()
    }
    item = item ++: properties
    map.put(name, item)
  }
  
  def putValue(name: String, value: Value): Unit = {
    put(name, "value", value)
  }
  
  def putType(name: String, value: Value): Unit = {
    put(name, "type", value)
  }
  
  def containsKey(key: String): Boolean = {
    map.contains(key)
  }
  
  def define(_pattern: Node, _value: Value): Unit = {
    if (_pattern.isInstanceOf[Name]) {
      val id: String = _pattern.asInstanceOf[Name].id
      val value: Value = lookUpLocal(id)
      if (value != null) {
        Log.error(_pattern, "trying to redefine name: " + id)
      }
      else {
        putValue(id, _value)
      }
    } 
    else if (_pattern.isInstanceOf[Tuple]) {
      
    } 
    else {
      
    }
  }
  
  def assign(_pattern: Node, _value: Value): Unit = {
    
  }
}

object Scope {
  def buildInitScope(): Scope = {
    val init: Scope = new Scope()
    init.putValue("+", new Add())
    init.putValue("-", new Sub())
    init.putValue("*", new Mult())
    init.putValue("/", new Div())

    init.putValue("<", new LT())
    init.putValue("<=", new LTE())
    init.putValue(">", new GT())
    init.putValue(">=", new GTE())
    init.putValue("=", new Eq())
    init.putValue("and", new And())
    init.putValue("or", new Or())
    init.putValue("not", new Not())

    init.putValue("#t", Type.BOOL)
    init.putValue("#f", Type.BOOL)

    init.putValue("Int", Type.INT)
    init.putValue("Bool", Type.BOOL)
    init.putValue("String", Type.STRING)
    init
  }
}