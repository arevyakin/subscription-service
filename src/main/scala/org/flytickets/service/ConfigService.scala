package org.flytickets.service

trait ConfigServiceComponent {
  def config: ConfigService
  trait ConfigService {

    def get(propName: String): String
    def getString(propName: String): String
    def getInt(propName: String): Int
    def getLong(propName: String): Long
    def getDouble(propName: String): Double
    def getBoolean(propName: String): Boolean

  }
}