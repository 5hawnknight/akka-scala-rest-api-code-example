#### akka-scala-rest-api-code-example

##### Requirements
* Docker
* SBT

#### Execution (Docker)
1) `sbt docker:publishLocal`
2) `docker run -it -p 9000:9000 akka-scala-rest-api-code-example:0.1`
* `http://localhost:9000/api/message`

#### Execution (Local)
* `sbt run`

#### Tests
* `sbt test`

#### Future Tasks
* Replace in memory list for database (slick,h2,derby,etc) 