package org.flytickets.util

import javax.mail._
import javax.mail.internet.{InternetAddress, MimeMessage}

import scala.collection.mutable.{ArrayBuffer, Map}

class MailConfiguration (val subject: String, val content: String, val recipients: Map[String, ArrayBuffer[InternetAddress]], val smtpConfig: SmtpConfiguration) {
  var message: Message = null
  setMessageProperties

  private def setMessageProperties {
    message = createMessage
    setToCcBccRecipients
    message.setSubject(subject)
    message.setText(content)
  }

  private def  setToCcBccRecipients {
    message.setRecipients(Message.RecipientType.TO, recipients("to").toArray)
    message.setRecipients(Message.RecipientType.CC, recipients("cc").toArray)
    message.setRecipients(Message.RecipientType.BCC, recipients("bcc").toArray)
  }

  private def createMessage: Message = {
    val properties = System.getProperties

    properties.put("mail.smtp.auth", "true")
    properties.put("mail.smtp.starttls.enable", "true")
    properties.put("mail.smtp.host", smtpConfig.host)
    properties.put("mail.smtp.port", smtpConfig.port)

    val session = Session.getDefaultInstance(properties, new Authenticator() {
      override def getPasswordAuthentication = new PasswordAuthentication(smtpConfig.user, smtpConfig.password)
    })

    new MimeMessage(session)
  }
 }


