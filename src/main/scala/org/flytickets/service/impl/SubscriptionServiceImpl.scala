package org.flytickets.service.impl


import org.flytickets.service.SubscriptionServiceComponent
import org.flytickets.util.validation.EmailValidator
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._
import org.flytickets.dao.Db
import org.flytickets.dao.model.Subscription
import org.flytickets.exception.{MissingDataRequestParameterException, InvalidDataRequestParameterException, DataNotFoundException}
import org.squeryl.PrimitiveTypeMode._
import org.flytickets.dao.model.Member
import org.squeryl.Query

trait SubscriptionServiceComponentImpl extends SubscriptionServiceComponent {
  trait SubscriptionServiceImpl extends SubscriptionService {
    def add(subscription: Subscription) {
      transaction {
        Db.subscription.insert(subscription)
      }
    }

    def remove(subscription: String) {
      transaction {
        val currentSubscription = Db.subscription.where(s => s.name === subscription)

        if (currentSubscription.isEmpty) {
          throw new DataNotFoundException("Not found subscription with name " + subscription)
        }
          Db.subscription.delete(currentSubscription)
      }
    }

    def get(name: String): Subscription = {
      transaction {
        val subscription = Db.subscription.where(s => s.name === name).headOption

        if (subscription.isEmpty) {
          throw new DataNotFoundException("Not found subscription with name " + name)
        }

        subscription.head
      }
    }

    def addMember(parameters: Map[String, Any]): JValue = {
      val subscriptionName = parameters.get("subscriptionName").mkString
      val email = parameters.get("email").mkString

      if (subscriptionName.isEmpty) {
        throw new MissingDataRequestParameterException("Subscription name key is missing")
      } else if (email.isEmpty) {
        throw new MissingDataRequestParameterException("Email keys are missing")
      }

      EmailValidator.validate(email)

      transaction {
        val member: Option[Member] = Db.members.where(m => m.email === email).headOption

        if (!member.isEmpty) {
          val subscription = new Subscription()
          subscription.name = subscriptionName
          subscription.email = email

          Db.subscription.insert(subscription)

          parse( """{"response":"done"}""")
        } else {
          throw new InvalidDataRequestParameterException("Name or Email was not found or the email exists in the list")
        }
      }
    }

    def removeMember(parameters: Map[String, Any]): JValue = {
      val subscriptionName = parameters.get("subscriptionName").mkString
      val email = parameters.get("email").mkString

      if (subscriptionName.isEmpty) {
        throw new MissingDataRequestParameterException("Subscription name key is missing")
      }

      if (parameters.get("email").isEmpty) {
        throw new MissingDataRequestParameterException("Email keys are missing")
      }

      EmailValidator.validate(email)

      transaction {
        val member: Option[Member] = Db.members.where(m => m.email === email).headOption

        if (!member.isEmpty) {
          val subscription: Query[Subscription] = Db.subscription.where(s => s.name === subscriptionName and s.email === email)

          if (subscription.headOption.isEmpty) {
            throw new DataNotFoundException("Name or Email was not found")
          }

          Db.subscription.delete(subscription)

          parse( """{"response":"done"}""")
        } else {
          throw new DataNotFoundException("Email %s was not found".format(email))
        }
      }
    }
  }
}
