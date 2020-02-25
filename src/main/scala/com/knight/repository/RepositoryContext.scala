package com.knight.repository

import akka.actor.ActorSystem

object RepositoryContext {
  lazy val actorSystem               = ActorSystem("akka-scala-rest-api-code-example")
  lazy val scheduler                 = actorSystem.scheduler
  implicit lazy val executionContext = actorSystem.dispatcher
}
