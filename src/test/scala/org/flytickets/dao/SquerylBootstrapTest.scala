package org.flytickets.dao

import org.flytickets.service.impl.ConfigServiceComponentTestImpl
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.H2Adapter
import java.sql.{DriverManager, SQLException}


object SquerylBootstrapTest {
  def initConcreteFactory(config: ConfigServiceComponentTestImpl#ConfigService) {
    val url = config.get("db.url")

    Class.forName("org.h2.Driver")

    SessionFactory.concreteFactory = Some{()=>
      try {
        Session.create(DriverManager.getConnection(url), new H2Adapter)
      } catch {
        case ex: SQLException => {
          throw ex
        }
      }
    }
  }
}