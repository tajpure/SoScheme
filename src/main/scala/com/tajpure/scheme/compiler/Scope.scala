package com.tajpure.scheme.compiler

import java.util.NoSuchElementException
import scala.collection.Set
import scala.collection.mutable.HashMap
import com.tajpure.scheme.compiler.ast.Name
import com.tajpure.scheme.compiler.ast.Node
import com.tajpure.scheme.compiler.ast.Symbol
import com.tajpure.scheme.compiler.ast.Tuple
import com.tajpure.scheme.compiler.llvm.CodeGen
import com.tajpure.scheme.compiler.util.Log
import com.tajpure.scheme.compiler.value.Type
import com.tajpure.scheme.compiler.value.Value
import com.tajpure.scheme.compiler.value.premitives.Add
import com.tajpure.scheme.compiler.value.premitives.And
import com.tajpure.scheme.compiler.value.premitives.Display
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
import org.jllvm.value.user.constant.ConstantBoolean
import com.tajpure.scheme.compiler.value.BoolValue
import com.tajpure.scheme.compiler.value.premitives.ListFunc
import com.tajpure.scheme.compiler.exception.RunTimeException
import com.tajpure.scheme.compiler.value.premitives.Newline
import com.tajpure.scheme.compiler.value.premitives.Cons
import com.tajpure.scheme.compiler.value.premitives.Cdr
import com.tajpure.scheme.compiler.value.premitives.Car
import com.tajpure.scheme.compiler.value.premitives.IsString
import com.tajpure.scheme.compiler.value.premitives.IsNumber
import com.tajpure.scheme.compiler.value.premitives.IsPair
import com.tajpure.scheme.compiler.value.premitives.IsChar
import com.tajpure.scheme.compiler.value.premitives.IsProcedure
import com.tajpure.scheme.compiler.value.premitives.IsBoolean
import com.tajpure.scheme.compiler.value.premitives.SetValue
import com.tajpure.scheme.compiler.value.premitives.IsEqv
import com.tajpure.scheme.compiler.value.premitives.IsNull
import com.tajpure.scheme.compiler.value.premitives.Import
import com.tajpure.scheme.compiler.value.premitives.Append
import com.tajpure.scheme.compiler.value.premitives.FilterFunc
import com.tajpure.scheme.compiler.value.premitives.MapFunc

class Scope(_parent: Scope, _codegen: CodeGen) {

  val map = new HashMap[String, HashMap[String, Object]]

  val parent: Scope = _parent
  
  val codegen: CodeGen = _codegen
  
  // save inner scope for "REPL" 
  var innerScope: Scope = null

  def this() = this(null, null)
  
  def this(_parent: Scope) = this(_parent, _parent.codegen)
  
  def setInnerScope(s: Scope): Unit = {
    if (innerScope == null) {
      innerScope = s
    }
  }

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
    } 
    else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } 
    else {
      null
    }
  }

  def lookupLocal(name: String): Value = {
    val v: Object = lookupPropertyLocal(name, "value")
    if (v == null) {
      null
    } 
    else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } 
    else {
      null
    }
  }

  def lookupType(name: String): Value = {
    val v: Object = lookupProperty(name, "type")
    if (v == null) {
      null
    } 
    else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } 
    else {
      null
    }
  }

  def lookupLocalType(name: String): Value = {
    val v: Object = lookupPropertyLocal(name, "type")
    if (v == null) {
      null
    } 
    else if (v.isInstanceOf[Value]) {
      v.asInstanceOf[Value]
    } 
    else {
      null
    }
  }

  def lookupPropertyLocal(name: String, key: String): Object = {
    val item = map.get(name)
    if (!item.isEmpty) {
      try {
        item.get(key)
      } catch {
        case nsee: NoSuchElementException => null
        case e: Exception => Log.error(e.toString()); null
      }
    } else {
      null
    }
  }

  def lookupProperty(name: String, key: String): Object = {
    val v: Object = lookupPropertyLocal(name, key)
    if (v != null) {
      v
    } else if (parent != null) {
      parent.lookupProperty(name, key)
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
    } 
    else if (parent != null) {
      parent.findDefiningScope(name)
    } 
    else {
      null
    }
  }

  def put(name: String, key: String, value: Object): Unit = {
    val item: HashMap[String, Object] = 
      if (map.get(name).isEmpty) {
        new HashMap[String, Object]()
      } 
      else {
        map.get(name).get
      }
   
    item.put(key, value)
    map.put(name, item)
  }

  def putProperties(name: String, properties: HashMap[String, Object]): Unit = {
    val item: HashMap[String, Object] = 
      if (map.get(name).get == null) {
        new HashMap[String, Object]() ++: properties
      } 
      else {
        map.get(name).get ++: properties
      }
    
    map.put(name, item)
  }

  def putValue(name: String, value: Value): Unit = {
    put(name, "value", value)
  }

  def putType(name: String, value: Value): Unit = {
    put(name, "type", value)
  }

  def keySet(): Set[String] = {
    map.keySet
  }

  def containsKey(key: String): Boolean = {
    map.contains(key)
  }

  def define(_pattern: Node, _value: Value): Unit = {
    if (_pattern.isInstanceOf[Name]) {
      val id: String = _pattern.asInstanceOf[Name].id
      putValue(id, _value)
    } 
    else if (_pattern.isInstanceOf[Tuple]) {

    } 
    else {

    }
  }

  def assign(_pattern: Node, _value: Value): Unit = {
    // TODO
  }
  
  def save(path: String): Unit = {
    codegen.save(path)
  }
  
  def lookupLLVM(name: String): org.jllvm.value.Value = {
    val v: Object = lookupProperty(name, "llvm.value")
    if (v == null) {
      null
    } 
    else if (v.isInstanceOf[org.jllvm.value.Value]) {
      v.asInstanceOf[org.jllvm.value.Value]
    } 
    else {
      null
    }
  }

  def putValueLLVM(name: String, value: org.jllvm.value.Value): Unit = {
    put(name, "llvm.value", value)
  }
  
  override
  def toString(): String = {
    map.keySet.foldLeft("")(
        (content: String, key: String) => {
          map.get(key).foldLeft(content + Constants.PAREN_BEGIN + key){
            case (content, kv) => 
               content + " " + kv + Constants.PAREN_END + " "
          }
        })
  }

}

object Scope extends App {
  
  def buildIn(s: Scope): Scope = {
    s.putValue("+", new Add())
    s.putValue("-", new Sub())
    s.putValue("*", new Mult())
    s.putValue("/", new Div())

    s.putValue("<", new LT())
    s.putValue("<=", new LTE())
    s.putValue(">", new GT())
    s.putValue(">=", new GTE())
    s.putValue("=", new Eq())
    s.putValue("and", new And())
    s.putValue("or", new Or())
    s.putValue("not", new Not())
    s.putValue("display", new Display())
    s.putValue("list", new ListFunc())
    s.putValue("newline", new Newline())
    s.putValue("cons", new Cons())
    s.putValue("car", new Car())
    s.putValue("cdr", new Cdr())
    s.putValue("set!", new SetValue())
    s.putValue("boolean?", new IsBoolean())
    s.putValue("char?", new IsChar())
    s.putValue("boolean?", new IsBoolean())
    s.putValue("number?", new IsNumber())
    s.putValue("pair?", new IsPair())
    s.putValue("procedure?", new IsProcedure())
    s.putValue("string?", new IsString())
    s.putValue("null?", new IsNull())
    s.putValue("eqv?", new IsEqv())
    s.putValue("import", new Import())
    s.putValue("append", new Append())
    s.putValue("filter", new FilterFunc())
    s.putValue("map", new MapFunc())

    s.putValue("Int", Type.INT)
    s.putValue("Bool", Type.BOOL)
    s.putValue("String", Type.STRING)
    s
  }
  
  def buildInitScope(): Scope = {
    val init: Scope = new Scope()
    
    buildIn(init)

    init.putValue("#t", new BoolValue(true))
    init.putValue("#f", new BoolValue(false))
    
    init
  }
  
  def buildInitCompilerScope(codegen: CodeGen): Scope = {
    val init: Scope = new Scope(null, codegen)
    
    buildIn(init)

    init.putValueLLVM("#t", new ConstantBoolean(true))
    init.putValueLLVM("#f", new ConstantBoolean(false))

    init
  }
  
  def mergeDefault(properties: Scope, s: Scope): Unit = {
    properties.keySet().foreach {
      key =>
        val defaultValue: Object = properties.lookupPropertyLocal(key, "default")
        if (defaultValue == null) {
        }
        else if (defaultValue.isInstanceOf[Value]) {
          val existing: Value = s.lookup(key)
          if (existing == null) {
            s.putValue(key, defaultValue.asInstanceOf[Value])
          }
        }
        else {
          Log.error("default value is not a value")
        }
    }
  }

  def evalProperties(unevaled: Scope, s: Scope): Scope = {
    val evaled: Scope = new Scope()
    unevaled.keySet().map {
      field =>
        val props: HashMap[String, Object] = unevaled.lookupAllProps(field)
        props.foreach {
          case (k, v) =>
            if (v.isInstanceOf[Node]) {
              val value: Value = v.asInstanceOf[Node].interp(s)
              evaled.put(field, k, value)
            } else {
              Log.error("property is not a node, parser bug:" + v)
            }
        }
    }
    evaled
  }
  
}