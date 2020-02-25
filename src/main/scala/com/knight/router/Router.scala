package com.knight.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.knight.exceptions.DuplicateRequest
import com.knight.models.RawMessage
import com.knight.repository.MessageRepository

import scala.util.{Failure, Success}

trait Router {
  def route: Route
}

class MessageRouter(messageRepository: MessageRepository) extends Router with Directives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = pathPrefix("api") {
    pathPrefix("message") {
      pathEndOrSingleSlash {
        get {
          complete(messageRepository.all)
        } ~ post {
          entity(as[RawMessage]) { rawMessage =>
            onComplete(messageRepository.duplicateRequestValidation(rawMessage)) {
              case Success(Some(DuplicateRequest)) => {
                onComplete(messageRepository.getMessageCount) {
                  case Success(count) =>
                    complete(StatusCodes.OK, count)
                  case Failure(_) =>
                    complete(StatusCodes.NotFound, s"an error occurred trying to return message count")
                }
              }
              case Success(None) =>
                onComplete(messageRepository.addMessage(rawMessage)) {
                  case Success(count) =>
                    complete(StatusCodes.Created, count)
                  case Failure(_) =>
                    complete(
                      StatusCodes.NotFound,
                      s"an error occurred trying to add message with the following data : id = ${rawMessage.id} message = ${rawMessage.message}")
                }
              case Failure(_) =>
                complete(
                  StatusCodes.NotFound,
                  s"an error occurred trying to Post message with the following data : id = ${rawMessage.id} message = ${rawMessage.message}")
            }
          }
        }
      }
    }
  }
}
