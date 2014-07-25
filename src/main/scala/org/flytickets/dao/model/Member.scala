package org.flytickets.dao.model

import org.apache.commons.lang.builder.ToStringBuilder
import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column

class Member (
            var id: Long,
            var name: String,
            var email: String,
            @Column("type")
            var memberType: String
  ) extends KeyedEntity[Long] {

  def this() = this(0, "", "", "")

  override def toString = {
    new ToStringBuilder(this).
      append("id", id).
      append("email", email).
      append("name", name).
      append("type", memberType)
      toString
  }
}





