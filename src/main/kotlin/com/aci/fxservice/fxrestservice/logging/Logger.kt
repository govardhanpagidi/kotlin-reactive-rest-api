package com.aci.fxservice.fxrestservice.logging

import org.apache.logging.log4j.LogManager
class Logger : ILogger {
    private val log = LogManager.getLogger(Logger::class.java)

    override fun logInfo(message: String) {
        log.info(message)
    }

    override fun logError(message: String) {
        log.error(message)
    }

    override fun logDebug(message: String) {
        log.debug(message)
    }

    override fun logTrace(message: String) {
        log.trace(message)
    }

    override fun logWarn(message: String) {
        log.warn(message)
    }
}
