package com.aci.fxservice.fxrestservice.controller

import com.aci.fxservice.fxrestservice.model.response.ConversionResponse
import com.aci.fxservice.fxrestservice.model.request.ConversionRequest
import com.aci.fxservice.fxrestservice.model.request.FxRateRequest
import com.aci.fxservice.fxrestservice.model.response.FxRateResponse
import com.aci.fxservice.fxrestservice.service.ConversionService
import com.aci.fxservice.fxrestservice.service.FxRateService
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
@RequestMapping("/fxrates")
class FxRateController (
    private val fxRateService: FxRateService
){
    @GetMapping
    fun findAll() : Flux<FxRateResponse> = fxRateService.findFxRates()

    @PostMapping
    fun doConversion(@RequestBody fxRateRequest : FxRateRequest) : Mono<FxRateResponse> {
        return fxRateService.findFxRateById(fxRateRequest)
    }

}