package org.flytickets.service.impl

import java.util.Properties

import org.flytickets.service.ConfigServiceComponent


trait ConfigServiceComponentTestImpl extends ConfigServiceComponent {
  trait ConfigServiceTestImpl extends ConfigService {

    val CONFIG_PROPS_FILE = "/config/config_test.properties"
    private lazy val props: Properties = read(CONFIG_PROPS_FILE)

    def getString(propName: String): String = {
      var res = System.getProperty(propName, null)

      if (res == null) {
        res = props.getProperty(propName, null)
        //debug("Looking for config property [%s] in main config ...".format(propName))
      }
      if(res == null) throw new IllegalArgumentException("Missing property key: " + propName)
/*      if (log.isDebugEnabled) {
        val secureRes = if (!propName.toLowerCase.contains("password")) res else List.fill(res.length())('*').mkString
        debug("Found config property [%s]=[%s]".format(propName, secureRes))
      }*/
      res
    }

    // short version of getString
    def get(propName: String): String = getString(propName)

    /*
     @throws IllegalArgumentException if no property found
     @throws NumberFormatException if value is not Int
      */
    def getInt(propName: String): Int = getString(propName).toInt

    /*
     @throws IllegalArgumentException if no property found
     @throws NumberFormatException if value is not Long
      */
    def getLong(propName: String): Long = getString(propName).toLong

    /*
     @throws IllegalArgumentException if no property found
     @throws NumberFormatException if value is not Double
      */
    def getDouble(propName: String): Double = getString(propName).toDouble

    /*
     @throws IllegalArgumentException if no property found
     @throws NumberFormatException if value is not Boolean (not "true" or "false")
      */
    def getBoolean(propName: String): Boolean = getString(propName).toBoolean

    private def read(path: String, required: Boolean = true): Properties = {
      val props = new Properties()
      val s = getClass.getResourceAsStream(path)
      if (s == null) {
        if (required) throw new IllegalArgumentException("config file not found: " + path)
        else return null
      }
      try props.load(s)
      finally s.close()
      props
    }
  }
}