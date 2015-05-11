package com.tajpure.scheme.compiler.util

import scala.collection.mutable.LinkedHashMap

class LRUCache[K, V](_maxSize: Int) {
  
  private val maxSize = _maxSize
  
  private val map: LinkedHashMap[K, V] = new LinkedHashMap[K, V]()
  
  def put(key: K, value: V): Unit = {
    while (map.size >= maxSize) {
      removeEldestEntry()
    }
    map.put(key, value)
  }
  
  def get(key: K): Option[V] = {
    map.get(key)
  }
  
  def removeEldestEntry(): Unit = {
    map -= map.head._1
  }
  
  def size(): Int = {
    map.size
  }
  
}