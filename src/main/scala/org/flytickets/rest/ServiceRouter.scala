package org.flytickets.rest

import org.json4s.JsonAST.JObject
import org.json4s.jackson.JsonMethods._
import org.flytickets.context.Context
import org.flytickets.dao.model.{Member, Subscription}
import org.flytickets.util.{Mail, Logging}
import spray.http.MediaTypes._
import spray.httpx.Json4sSupport
import spray.routing._

trait ServiceRouter extends HttpService with Json4sSupport with Logging {

  val route =
    pathPrefix("api") {
      path("subscription" / "add") {
        post {
          entity(as[JObject]) { subscriptionObj =>
            complete {
              val rawSubscription = subscriptionObj.extract[JObject]
              val name = rawSubscription.values.get("name").mkString

              Context.subscriptionService.add(new Subscription(0, name, ""))

              info("Add Subscription request executed successfully")
              parse("""{"response":"done"}""")
            }
          }
        }
      } ~ path("subscription" / "remove") {
        post {
          entity(as[JObject]) { subscriptionObj =>
            complete {
              val rawSubscription = subscriptionObj.extract[JObject]
              val name = rawSubscription.values.get("name").mkString

              Context.subscriptionService.remove(name)

              info("Remove Subscription request executed successfully")
              parse( """{"response":"done"}""")
            }
          }
        }
      } ~ path("subscription" / "get") {
        get {
          parameters('name.as[String]) { name =>
            respondWithMediaType(`application/json`) {
              complete {
                val jsonResponse = Context.subscriptionService.get(name)

                info("Get Subscription request executed successfully")

                jsonResponse
              }
            }
          }
        }
      } ~ path("email" / "send") {
        post {
          entity(as[JObject]) { emailObj =>
            complete {
              val email = emailObj.extract[Mail]

              Context.emailService.send(email)

              info("Send Email request executed successfully")
              parse( """{"response":"done"}""")
            }
          }
        }
      } ~ path("member" / "add") {
        post {
          entity(as[JObject]) { memberObj =>
            complete {
              val rawMember = memberObj.extract[JObject]

              val member = new Member()
              member.name = rawMember.values.get("name").mkString
              member.email = rawMember.values.get("email").mkString
              member.memberType = rawMember.values.get("type").mkString

              Context.memberService.add(member)

              info("Add Member request executed successfully")
              parse( """{"response":"done"}""")
            }
          }
        }
      } ~ path("member" / "remove") {
        post {
          entity(as[JObject]) { memberObj =>
            complete {
              val rawMember = memberObj.extract[JObject]
              val member = new Member()
              member.name = rawMember.values.get("name").mkString
              member.email = rawMember.values.get("email").mkString

              Context.memberService.delete(member)

              info("Remove Member request executed successfully")
              parse( """{"response":"done"}""")
            }
          }
        }
      } ~ path("member" / "edit") {
        post {
          entity(as[JObject]) { jObject =>
            complete {
              val mapValues = jObject.values

              Context.memberService.edit(mapValues)

              info("Edit Member request executed successfully")
              parse( """{"response":"done"}""")
            }
          }
        }
      } ~ path("member" / "get") {
        post {
          entity(as[JObject]) { memberObj =>
            complete {
              val rawMember = memberObj.extract[JObject]
              val email = rawMember.values.get("email").mkString
              val member: Member = Context.memberService.get(email)

              info("Get Member request executed successfully")

              member
            }
          }
        }
      } ~ path("subscription" / "member" / "add") {
        post {
          entity(as[JObject]) { jObject =>
            complete {
              val mapValues = jObject.values
              val jsonResponse = Context.subscriptionService.addMember(mapValues)

              info("Add Member To Subscription request executed successfully")
              jsonResponse
            }
          }
        }
      } ~ path("subscription" / "member" / "remove") {
        post {
          entity(as[JObject]) { jObject =>
            complete {
              val mapValues = jObject.values
              val jsonResponse = Context.subscriptionService.removeMember(mapValues)

              info("Remove Member From Subscription request executed successfully")
              jsonResponse
            }
          }
        }
      }
    }
}
