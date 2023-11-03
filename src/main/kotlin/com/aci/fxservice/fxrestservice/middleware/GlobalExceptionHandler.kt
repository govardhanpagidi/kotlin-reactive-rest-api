package com.aci.fxservice.fxrestservice.middleware
import com.aci.fxservice.fxrestservice.logging.ILogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(private val logger: ILogger) {
    val internalErrorMessage = "an error occurred, try again later"
    @ExceptionHandler(InternalError::class)
    fun handleGlobalExceptions(e: Exception): ResponseEntity<String> {
        // TODO: Respond in generic Json format, currently it is just a string
        logger.logError(e.message ?: internalErrorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(internalErrorMessage)
    }
}
