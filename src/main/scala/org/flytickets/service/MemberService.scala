package org.flytickets.service

import org.flytickets.dao.model.Member

trait MemberServiceComponent {
  def memberService: MemberService

  trait MemberService {
    def add(member: Member)

    def delete(member: Member)

    def edit(parameters: Map[String, Any])

    def get(email: String): Member
  }
}
