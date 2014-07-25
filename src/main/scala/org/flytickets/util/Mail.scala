package org.flytickets.util

case class Mail(var subscriptionName: String, val title: String, val message: String) {
  require(!subscriptionName.isEmpty, "Subscription name must not be empty")
  require(!title.isEmpty, "Title of email must not be empty")
  require(!message.isEmpty, "Message of email must not be empty")
}


//class Mail extends Actor {
//
//  def receive = {
//    case mailConfig: MailConfiguration => {
//      Transport.send(mailConfig.message)
//
//      println("All emails were sent to specified recipients " + mailConfig.recipients + " successfully")
//    }
//
//    case _ => println("Please give me MailConfiguration")
//  }
//}
