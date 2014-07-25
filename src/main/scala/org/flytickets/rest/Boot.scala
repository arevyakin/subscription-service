package org.flytickets.rest

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import org.flytickets.context.Context
import org.flytickets.dao.SquerylBootstrap
import spray.can.Http

object Boot extends App {
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("subscription-service")

  // create and start our service actor
  val service = system.actorOf(Props[ServiceRouterActor], "demo-service")

  //init a data base
  SquerylBootstrap.initConcreteFactory(Context.config)

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}
