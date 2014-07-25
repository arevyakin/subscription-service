package org.flytickets.context

import org.flytickets.service.impl._

object Context extends ConfigServiceComponentImpl with SubscriptionServiceComponentImpl with
  EmailServiceComponentImpl with MemberServiceComponentImpl {

  def config: ConfigService = new ConfigServiceImpl {}

  def subscriptionService: SubscriptionService = new SubscriptionServiceImpl {}

  def emailService: EmailService = new EmailServiceImpl {}

  def memberService: MemberService = new MemberServiceImpl {}
}
