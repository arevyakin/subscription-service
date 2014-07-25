package org.flytickets.util.validation

import javax.mail.internet.{AddressException, InternetAddress}
import org.flytickets.exception.EmailAddressException

object EmailValidator {
  def validate(email: String): Boolean = {
    try {
      new InternetAddress(email).validate()

      true
    } catch {
      case ae: AddressException => {
        throw new EmailAddressException(email + " recipient has incorrect address")
      }
    }
  }
}
