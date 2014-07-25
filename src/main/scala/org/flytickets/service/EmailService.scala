package org.flytickets.service

import org.flytickets.util.Mail

trait EmailServiceComponent {
  def emailService: EmailService

  trait EmailService {
    def send(email: Mail)
  }
}
