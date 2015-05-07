package com.tajpure.scheme.compiler.util

import java.util.LinkedHashMap

class LRUCache[K, V](_maxSize: Int) {
  
  private val maxSize = _maxSize
  
  var size = 0
  
  private val map: LinkedHashMap[K, V] = new LinkedHashMap[K, V]()
  
  def put(key: K, value: V): Unit = {
//    while (map.size >= maxSize) {
//      remove()
//    }
    map.put(key, value)
    size = map.size
  }
  
  def get(key: K): V = {
    map.get(key)
  }
  
  def remove(): Unit = {
//    map.
    size = map.size
  }
  
}