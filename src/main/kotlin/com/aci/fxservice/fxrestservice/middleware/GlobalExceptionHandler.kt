package com.aci.fxservice.fxrestservice.middleware
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGlobalExceptions(e: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred: ${e.message}")
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusExceptions(e: ResponseStatusException): ResponseEntity<String> {
        return ResponseEntity.status(e.statusCode).body(e.reason)
    }

}
