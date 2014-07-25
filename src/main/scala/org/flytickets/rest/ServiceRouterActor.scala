package org.flytickets.rest

import akka.actor.Actor
import org.json4s.{DefaultFormats, Formats}
import org.flytickets.exception.CustomException

class ServiceRouterActor extends Actor with ServiceRouter with CustomException {

  implicit def json4sFormats: Formats = DefaultFormats

  def actorRefFactory = context

  def receive = runRoute((handleExceptions(customExceptionHandler)(route)))
}
