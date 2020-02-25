package com.knight

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding

import scala.concurrent.{ExecutionContext, Future}
import com.knight.router.Router

class Server(router: Router, host: String, port: Int)(implicit system: ActorSystem, executionContext: ExecutionContext) {
  def bind(): Future[ServerBinding] = Http().bindAndHandle(router.route, host, port)
}
