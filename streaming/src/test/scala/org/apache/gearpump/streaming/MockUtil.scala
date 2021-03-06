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
package org.apache.gearpump.streaming

import akka.actor.{ActorSystem, Actor, ActorRef, ScalaActorRef}
import akka.testkit.TestActorRef
import org.apache.gearpump.Message
import org.apache.gearpump.streaming.task.TaskContext
import org.mockito.{Mockito, ArgumentMatcher}
import org.mockito.Mockito
import org.mockito.Matchers

object MockUtil {

  lazy val system: ActorSystem = ActorSystem("mockUtil")

  def mockTaskContext: TaskContext = {
    val context = Mockito.mock(classOf[TaskContext])
    Mockito.when(context.self).thenReturn(Mockito.mock(classOf[TestActorRef[Actor]]))
    Mockito.when(context.system).thenReturn(system)
    context
  }

  def argMatch[T](func: T => Boolean) : T = {
    Matchers.argThat(new ArgumentMatcher[T] {
      override def matches(param: Any): Boolean = {
        val mesage = param.asInstanceOf[T]
        func(mesage)
      }
    })
  }
}