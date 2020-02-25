package com.knight

import akka.actor.ActorSystem
import com.knight.library.ConfigurationFactory.configuration
import com.knight.library.Logger.log
import com.knight.repository.MessageRepository
import com.knight.router.MessageRouter

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.util.{Failure, Success}

object Main extends App {
  val host = configuration.getString("app.host")
  val port = configuration.getInt("app.port")

  implicit val system: ActorSystem          = ActorSystem()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val messageRepository = new MessageRepository()
  val router            = new MessageRouter(messageRepository)
  val server            = new Server(router, host, port)

  val binding = server.bind()
  binding.onComplete {
    case Success(server)    => log.info(s"server bound to ${server.localAddress}")
    case Failure(exception) => log.error(exception, "Failed to bind to {}:{}!", host, port)
  }
  Await.result(system.whenTerminated, Duration.Inf)
}
