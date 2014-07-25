package org.flytickets.data

import javax.mail.internet.InternetAddress

import org.flytickets.util.Logging
import org.flytickets.util.validation.EmailValidator

import scala.collection.mutable.ArrayBuffer

class Addresses() extends Logging {

  val collection = scala.collection.mutable.Map("to" -> new ArrayBuffer[InternetAddress],
                           "cc" -> new ArrayBuffer[InternetAddress],
                           "bcc" -> new ArrayBuffer[InternetAddress])

  def add(email: String, recipientType: String = "to") {
    val isValidEmail: Boolean = EmailValidator.validate(email)

    val hasSameEmailAddr: Boolean = {
      if (collection(recipientType).exists(_.getAddress().equals(email))) {
        warn(email + " recipient has same value in recipient type buffer")
        true
      } else false
    }

    if (isValidEmail && !hasSameEmailAddr) collection(recipientType) += new InternetAddress(email)
  }

  def remove(email: String, recipientType: String = "") {
    if (recipientType.isEmpty) {
      val errMessage = "Incorrect type of recipient"
      error(errMessage, new Exception(errMessage))

      throw new Exception (errMessage)
    }

    val idx = collection(recipientType).indexWhere(_.getAddress().equals(email))

    if (idx != -1) {
        collection(recipientType).remove(idx)
    } else {
      val errMessage = email + " recipient was not found in recipient type buffer"
      error(errMessage, new ArrayIndexOutOfBoundsException(errMessage))

      throw new ArrayIndexOutOfBoundsException (errMessage)
    }
  }

  def clear() {
    collection.clear()
  }
}
