package com.knight.tests

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knight.models.RawMessage
import com.knight.models.tiny.Count
import com.knight.repository.MessageRepository
import com.knight.router.MessageRouter
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MessageRouteSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  "Router" should {
    "return count of 2 with a json message string of `one two`" in {
      val messageRepository = new MessageRepository()
      val router            = new MessageRouter(messageRepository)
      val validRawMessage   = RawMessage("123", "one two")

      Post("/api/message", validRawMessage) ~> router.route ~> check {
        status mustBe StatusCodes.Created
        val resp = responseAs[Count]
        resp.count mustBe 2
      }
    }
    "ignore duplicate requests" in {
      val messageRepository = new MessageRepository()
      val router            = new MessageRouter(messageRepository)
      val rawMessage        = RawMessage("123", "one two")

      Post("/api/message", rawMessage) ~> router.route
      Post("/api/message", rawMessage) ~> router.route ~> check {
        status mustBe StatusCodes.OK
        val resp = responseAs[Count]
        resp.count mustBe 2
      }
    }
    "return count 6 after multiple requests" in {
      val messageRepository = new MessageRepository()
      val router            = new MessageRouter(messageRepository)
      val rawMessageOne     = RawMessage("001", "one two")
      val rawMessageTwo     = RawMessage("002", "three four")
      val rawMessageThree   = RawMessage("003", "five one")

      Post("/api/message", rawMessageOne) ~> router.route
      Post("/api/message", rawMessageTwo) ~> router.route
      Post("/api/message", rawMessageThree) ~> router.route ~> check {
        status mustBe StatusCodes.Created
        val resp = responseAs[Count]
        resp.count mustBe 6
      }
    }
  }
}
