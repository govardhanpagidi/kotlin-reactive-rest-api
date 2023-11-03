package com.aci.fxservice.fxrestservice.controller

import com.aci.fxservice.fxrestservice.model.response.ConversionResponse
import com.aci.fxservice.fxrestservice.model.request.ConversionRequest
import com.aci.fxservice.fxrestservice.model.request.FxRateRequest
import com.aci.fxservice.fxrestservice.model.response.FxDataResponse
import com.aci.fxservice.fxrestservice.model.response.FxRateResponse
import com.aci.fxservice.fxrestservice.service.ConversionService
import com.aci.fxservice.fxrestservice.service.FxDataService
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
@RequestMapping("/fxdata")
class FxDataController (
    private val fxDataService: FxDataService
){
    @GetMapping
    fun findAll() : Flux<FxDataResponse> = fxDataService.findFxRates()

}