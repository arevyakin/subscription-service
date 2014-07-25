package org.flytickets.exception

import org.flytickets.util.Logging

class MissingDataRequestParameterException(msg: String) extends RuntimeException(msg) with Logging { error(msg, this) }