package org.flytickets.exception

import org.flytickets.util.Logging

class DataNotFoundException (msg: String) extends RuntimeException(msg) with Logging { error(msg, this) }
