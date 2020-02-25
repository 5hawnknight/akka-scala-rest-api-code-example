enablePlugins(JavaAppPackaging, DockerPlugin, AshScriptPlugin)

name := "akka-scala-rest-api-code-example"

version := "0.1"
scalaVersion := "2.13.1"

dockerBaseImage := "openjdk:8-jre-alpine"
packageName in Docker := "akka-scala-rest-api-code-example"

lazy val akkaHttpVersion = "10.1.11"
lazy val akkaVersion     = "2.6.3"
lazy val circeVersion    = "0.12.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka"    %% "akka-http"                % akkaHttpVersion,
  "com.typesafe.akka"    %% "akka-http-spray-json"     % akkaHttpVersion,
  "com.typesafe.akka"    %% "akka-actor-typed"         % akkaVersion,
  "com.typesafe.akka"    %% "akka-stream"              % akkaVersion,
  "de.heikoseeberger"    %% "akka-http-circe"          % "1.31.0",
  "io.circe"             %% "circe-generic"            % circeVersion,
  "ch.qos.logback"       % "logback-classic"           % "1.2.3",
  "com.typesafe.akka"    %% "akka-http-testkit"        % akkaHttpVersion % Test,
  "com.typesafe.akka"    %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest"        % "scalatest_2.13"            % "3.1.1" % "test",
  "com.github.javafaker" % "javafaker"                 % "1.0.2"
)
