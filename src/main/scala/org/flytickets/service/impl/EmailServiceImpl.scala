package org.flytickets.service.impl

import javax.mail.Transport

import com.typesafe.config.ConfigFactory
import org.flytickets.data._
import org.flytickets.dao.Db
import org.flytickets.service.EmailServiceComponent
import org.flytickets.util.validation.EmailValidator
import org.flytickets.util.{Mail, SmtpConfiguration, MailConfiguration}
import org.squeryl.PrimitiveTypeMode._

trait EmailServiceComponentImpl extends EmailServiceComponent{
  trait EmailServiceImpl extends EmailService {
    def send(email: Mail) {
      val conf = ConfigFactory.load("smtp")
      val host = conf.getString("svc.smtp.config.host")
      val port = conf.getString("svc.smtp.config.port")
      val user = conf.getString("svc.smtp.config.user")

      EmailValidator.validate(user)

      val password = conf.getString("svc.smtp.config.password")
      val smtpConfig = new SmtpConfiguration(host, port, user, password)

      val addrs = new Addresses
      transaction {
        from(Db.members)(m => select(m)).foreach(r => addrs.add(r.email))
      }

      val mailConfig = new MailConfiguration(email.title, email.message, addrs.collection, smtpConfig)

      Transport.send(mailConfig.message)
    }
  }
}
