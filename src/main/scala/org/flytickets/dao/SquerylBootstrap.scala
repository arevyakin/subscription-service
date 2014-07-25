package org.flytickets.dao

import org.flytickets.service.ConfigServiceComponent
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLInnoDBAdapter
import org.apache.commons.dbcp.BasicDataSource
import java.sql.SQLException
import org.flytickets.util.Logging


object SquerylBootstrap extends Logging{
  def initConcreteFactory(config: ConfigServiceComponent#ConfigService) {
    val jdbcDriver = "com.mysql.jdbc.Driver"

    val adapter = new MySQLInnoDBAdapter

    val dbcp = new BasicDataSource()
    dbcp.setDriverClassName(jdbcDriver)
    dbcp.setUrl(config.getString("db.url"))
    dbcp.setUsername(config.getString("db.login"))
    dbcp.setPassword(config.getString("db.password"))
    dbcp.setInitialSize(config.getInt("db.pool.initialSize"))
    dbcp.setMaxActive(config.getInt("db.pool.maxActive"))
    dbcp.setMaxIdle(config.getInt("db.pool.maxIdle"))
    dbcp.setMinEvictableIdleTimeMillis(config.getLong("db.pool.minIdleTimeSeconds") * 1000)
    dbcp.setMaxWait(config.getLong("db.pool.maxWaitSeconds") * 1000)
    dbcp.setValidationQuery("SELECT NOW()")
    dbcp.setTestOnReturn(true)
    dbcp.setTestOnBorrow(true)

    Class.forName(jdbcDriver)
    SessionFactory.concreteFactory = Some{()=>
      val session =
        try {
          Session.create(dbcp.getConnection, adapter)
        } catch {
          case ex: SQLException => {
            fatal("Failed to establish DB connection. \r\nURL=%s\r\nLogin=%s\r\n".format(dbcp.getUrl, dbcp.getUsername), ex)
            throw ex
          }
        }
      //val sqlLogger = getLog("marchex.dao.squeryl.sql")
      //session.setLogger(sqlLogger.debug(_))
      session
    }
    info("Done initialization of datasource pool and session factory.")
  }
}