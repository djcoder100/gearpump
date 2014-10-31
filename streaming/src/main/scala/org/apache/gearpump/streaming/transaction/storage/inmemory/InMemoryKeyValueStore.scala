/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.gearpump.streaming.transaction.storage.inmemory

import java.util

import org.apache.gearpump.streaming.transaction.storage.api.KeyValueStore

object InMemoryKeyValueStore {

}

class InMemoryKeyValueStore[K, V] extends KeyValueStore[K, V] {

  private val store = new util.HashMap[K, V]

  override def close(): Unit = Unit

  override def flush(): Unit = Unit

  override def delete(key: K): Option[V] = {
    Some(store.remove(key))
  }

  override def putAll(kvs: List[(K, V)]): Unit = {
    kvs.foreach(kv => store.put(kv._1, kv._2))
  }

  override def put(key: K, value: V): Option[V] = {
    Option(store.put(key, value))
  }

  override def get(key: K): Option[V] = {
    Option(store.get(key))
  }
}
