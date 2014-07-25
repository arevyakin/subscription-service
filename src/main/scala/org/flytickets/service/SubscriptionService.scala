package org.flytickets.service

import org.flytickets.dao.model.Subscription
import org.json4s.JsonAST.JValue

trait SubscriptionServiceComponent {
  def subscriptionService: SubscriptionService

  trait SubscriptionService {
    def add(subscription: Subscription)

    def remove(subscription: String)

    def get(name: String):  Subscription

    def addMember(parameters: Map[String, Any]): JValue

    def removeMember(parameters: Map[String, Any]): JValue
  }
}
