package com.tajpure.scheme.compiler

import scala.collection.mutable.HashMap
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.Value

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

  def loolUpLocal(name: String): Value = {
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
    if (item != null) {
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
}