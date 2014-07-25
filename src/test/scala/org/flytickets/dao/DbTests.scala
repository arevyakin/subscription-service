package org.flytickets.dao


import org.flytickets.service.impl.ConfigServiceComponentTestImpl
import org.flytickets.util.validation.MemberTypeValidator
import org.scalatest.junit.AssertionsForJUnit
import org.flytickets.context.Context
import org.flytickets.dao.model.{Subscription, Member}
import org.flytickets.exception.DataNotFoundException
import org.squeryl.PrimitiveTypeMode._

object DbTests extends App with AssertionsForJUnit with ConfigServiceComponentTestImpl {
  override def config: ConfigService = new ConfigServiceTestImpl {}

  SquerylBootstrapTest.initConcreteFactory(config)

  transaction {
    Db.create

    val testMember = new Member()
    testMember.email = "test@mail.ua"
    testMember.name = "test"
    testMember.memberType = MemberTypeValidator.Organization.toString

    val memberService: Context.MemberService = Context.memberService

    val params: Map[String, Any] = Map("oldName" -> "test", "newName" -> "test1", "oldEmail" -> "test@mail.ua", "newEmail" -> "test@mail.org")

    memberService.add(testMember)
    assertResult(testMember.email)(memberService.get("test@mail.ua").email)

    memberService.edit(params)
    assertResult("test@mail.org")(memberService.get("test@mail.org").email)
    assertResult("test1")(memberService.get("test@mail.org").name)

    testMember.email = "test@mail.org"
    memberService.delete(testMember)
    intercept[DataNotFoundException](memberService.get("test@mail.org"))

    println("CRUD test for the Member passed.")

    val testSubscription = new Subscription()
    testSubscription.name = "Microsoft"

    val subscriptionService: Context.SubscriptionService = Context.subscriptionService

    subscriptionService.add(testSubscription)
    assertResult(testSubscription.name)(subscriptionService.get("Microsoft").name)

    val member: Map[String, Any] = Map("email" -> "test@mail.org", "subscriptionName" -> "Microsoft")
    memberService.add(testMember)
    subscriptionService.addMember(member)
    //todo: workaround - the database implementation is not correct
    assertResult("test@mail.org")(Db.subscription.where(s => s.name === "Microsoft" and s.email === "test@mail.org").head.email)

    subscriptionService.removeMember(member)
    //todo: workaround - the database implementation is not correct
    assertResult(true)(Db.subscription.where(s => s.name === "Microsoft" and s.email === "test@mail.org").headOption.isEmpty)

    subscriptionService.add(testSubscription)
    assertResult(testSubscription.name)(subscriptionService.get("Microsoft").name)
    subscriptionService.remove(testSubscription.name)
    intercept[DataNotFoundException](subscriptionService.get("Microsoft"))

    println("CRUD test for the Subscription passed.")
  }
}
