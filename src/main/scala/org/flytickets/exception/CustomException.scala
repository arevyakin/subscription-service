package org.flytickets.exception

import spray.routing.{HttpService, ExceptionHandler}
import spray.http.MediaTypes._
import spray.http.StatusCodes
import org.flytickets.util.Logging


trait CustomException extends HttpService with Logging {
  implicit def customExceptionHandler =
    ExceptionHandler.apply {
      case irp: InvalidDataRequestParameterException => {
        respondWithMediaType(`application/json`) {
          ctx => ctx.complete(StatusCodes.BadRequest, irp.getMessage)
        }
      }
      case mrp: MissingDataRequestParameterException => {
        respondWithMediaType(`application/json`) {
          ctx => ctx.complete(StatusCodes.NotFound, mrp.getMessage)
        }
      }
    }
}
