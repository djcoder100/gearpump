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

package org.apache.gearpump.services

import org.apache.gearpump.cluster.MasterToAppMaster.AppMasterData
import org.apache.gearpump.cluster.TestUtil.MiniCluster
import org.apache.gearpump.cluster.{TestUtil}
import org.apache.gearpump.services.AppMasterService.{StreamingAppMasterDataDetail}
import org.apache.gearpump.streaming.{StreamingTestUtil}
import org.apache.gearpump.util.{LogUtil}
import org.scalatest.{BeforeAndAfterAll, Matchers, FlatSpec}
import org.slf4j.Logger
import spray.testkit.{ScalatestRouteTest}

import scala.concurrent.duration._

class AppMasterServiceSpec extends FlatSpec with ScalatestRouteTest with AppMasterService with Matchers with BeforeAndAfterAll {
  import upickle._
  private val LOG: Logger = LogUtil.getLogger(getClass)
  def actorRefFactory = system

  var miniCluster:MiniCluster = null
  def master = miniCluster.mockMaster

  override def beforeAll: Unit = {
    miniCluster = TestUtil.startMiniCluster
    StreamingTestUtil.startAppMaster(miniCluster, 0)
  }

  override def afterAll: Unit = {
    miniCluster.shutDown()
  }

  import StreamingAppMasterDataDetail._
  "AppMasterService" should "return a JSON structure for GET request when detail = false" in {
    implicit val customTimeout = RouteTestTimeout(15.seconds)
    (Get("/appmaster/0?detail=false") ~> appMasterRoute).asInstanceOf[RouteResult] ~> check{
      val responseBody = response.entity.asString
      read[AppMasterData](responseBody)
    }

    //TODO: fix this UT
//    (Get("/appmaster/streaming/0?detail=true") ~> appMasterRoute).asInstanceOf[RouteResult] ~> check{
//      val responseBody = response.entity.asString
//      read[StreamingAppMasterDataDetail](responseBody)
//    }
  }
}
