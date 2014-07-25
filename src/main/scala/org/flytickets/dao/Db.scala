package org.flytickets.dao

import org.flytickets.dao.model.{Subscription, Member}
import org.squeryl.Schema

object Db extends Schema {
  val members = table[Member]
  val subscription = table[Subscription]
}
