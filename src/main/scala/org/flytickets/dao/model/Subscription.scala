package org.flytickets.dao.model

import org.apache.commons.lang.builder.ToStringBuilder
import org.squeryl.KeyedEntity

class Subscription(var id: Long, var name: String, var email: String) extends KeyedEntity[Long] {
  def this() = this(0, "", "")

  override def toString = {
    new ToStringBuilder(this).
      append("id", id).
      append("email", email).
      append("name", name)
    toString
  }
}
