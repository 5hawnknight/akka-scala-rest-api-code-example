package com.knight.library

import akka.event.Logging

object Logger {
  import com.knight.repository.RepositoryContext._
  val log = Logging(actorSystem.eventStream, "akka-scala-rest-api-code-example")
}
