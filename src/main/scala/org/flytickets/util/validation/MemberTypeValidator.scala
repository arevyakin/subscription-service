package org.flytickets.util.validation

object MemberTypeValidator extends Enumeration {
  type MemberTypes = Value
  val Member, Organization = Value

  def validate(str: String): Boolean = {
    var result = false

    values.foreach{v =>
      if (v.toString == str) {
        return true
      }
    }

    result
  }
}
