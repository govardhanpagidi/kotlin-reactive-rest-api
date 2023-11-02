package com.aci.fxservice.fxrestservice.controller

import com.aci.fxservice.fxrestservice.model.response.ConversionResponse
import com.aci.fxservice.fxrestservice.model.request.ConversionRequest
import com.aci.fxservice.fxrestservice.service.ConversionService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/conversions")
class ConversionController (
        private val conversionService: ConversionService
){
    @GetMapping
    fun findAll() : Flux<ConversionResponse> = conversionService.findConversions()

    @GetMapping("/{id}")
    fun findConversionById(@PathVariable id:Long) : Mono<ConversionResponse> = conversionService.findConversionById(id)

    @PostMapping
    fun doConversion(@RequestBody institutionRequest : ConversionRequest) : Mono<ConversionResponse> {
        return conversionService.doConversion(institutionRequest)
    }

    @DeleteMapping()
    fun deleteAll() : Mono<Void> = conversionService.deleteAllConversions()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id:Long) : Mono<Void> = conversionService.deleteConversionById(id)

}