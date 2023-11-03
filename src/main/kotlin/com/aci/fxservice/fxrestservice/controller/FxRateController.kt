package com.aci.fxservice.fxrestservice.controller

import com.aci.fxservice.fxrestservice.model.request.FxRateByIDRequest
import com.aci.fxservice.fxrestservice.model.request.FxRateRequest
import com.aci.fxservice.fxrestservice.model.response.FxRateResponse
import com.aci.fxservice.fxrestservice.service.FxRateService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/fxrates")
class FxRateController (
    private val fxRateService: FxRateService
){
    @GetMapping
    fun findAllFxRates() : Flux<FxRateResponse> = fxRateService.findFxRates()

    @PostMapping
    fun saveFxRate(@RequestBody fxRateRequest : FxRateRequest) : Mono<FxRateResponse> {
        return fxRateService.saveFxRate(fxRateRequest)
    }

    @PutMapping
    fun updateFxRate(@RequestBody fxRateRequest : FxRateRequest) : Mono<FxRateResponse> {
        return fxRateService.saveFxRate(fxRateRequest)
    }

    @GetMapping("/{tenantId}/{bankId}/{baseCurrency}/{currency}")
    fun findFxRateById(
        @PathVariable("tenantId") tenantId: Int,
                         @PathVariable("bankId") bankId: Int,
                         @PathVariable("baseCurrency") baseCurrency: String,
                         @PathVariable("currency") currency: String
    )
    : Flux<FxRateResponse> {
        val fxRateByIDRequest = FxRateByIDRequest(
            tenantId,
            bankId,
            baseCurrency,
            currency
        )
        return fxRateService.findFxRateById(fxRateByIDRequest)
    }

    @DeleteMapping("/{tenantId}/{bankId}/{baseCurrency}/{currency}")
    fun deleteFxRateById(
        @PathVariable("tenantId") tenantId: Int,
        @PathVariable("bankId") bankId: Int,
        @PathVariable("baseCurrency") baseCurrency: String,
        @PathVariable("currency") currency: String
    )
    : Flux<Void> {
        val fxRateDeleteRequest = FxRateByIDRequest(
            tenantId,
            bankId,
            baseCurrency,
            currency
        )
        return fxRateService.deleteFxRate(fxRateDeleteRequest)
    }

}