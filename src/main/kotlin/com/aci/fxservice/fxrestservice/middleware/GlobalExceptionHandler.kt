package com.aci.fxservice.fxrestservice.middleware
import com.aci.fxservice.fxrestservice.logging.ILogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(private val logger: ILogger) {

    @ExceptionHandler(Exception::class)
    fun handleGlobalExceptions(e: Exception): ResponseEntity<String> {
        logger.logError(e.message!!);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred, try again later")
    }

}
