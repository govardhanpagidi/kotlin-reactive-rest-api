package com.aci.fxservice.fxrestservice.logging

interface ILogger {
    fun logInfo(message: String)
    fun logError(message: String)
    fun logDebug(message: String)
    fun logTrace(message: String)
    fun logWarn(message: String)
}