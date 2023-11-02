package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.FxRateData
import com.aci.fxservice.fxrestservice.entity.FxRateKey
import com.aci.fxservice.fxrestservice.logging.ILogger
import com.aci.fxservice.fxrestservice.model.request.FxRateRequest
import com.aci.fxservice.fxrestservice.model.response.FxRateResponse
import com.aci.fxservice.fxrestservice.repository.FxRateDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class FxRateService(
    private val fxRateDataRepository: FxRateDataRepository,
    private val logger: ILogger,
) {
    // find all conversions
    fun findFxRates() : Flux<FxRateResponse> {
        logger.logInfo("Received request to find all FxRates")
        // convert to dto after findAll
        return fxRateDataRepository
            .findAll()
            .map {
                mapToFxRateResponse(it)
            }
    }

    // find conversion by id
    fun findFxRateById(request: FxRateRequest) : Mono<FxRateResponse> {
        logger.logInfo("Received request to find FxRate by id: $request")
        // convert to dto after find
        return fxRateDataRepository.findFxRateDataByTenantIdAndBankIdAndBaseCurrencyAndCurrency(
           request.tenantId,
            request.bankId,
            request.baseCurrency,
            request.currency
        )
            .map {
                mapToFxRateResponse(it)
            }
    }

    fun saveFxRate(fxRate: FxRateData): Mono<FxRateResponse> {
        logger.logInfo("Received request to save FxRate: $fxRate")
        return fxRateDataRepository.save(fxRate)
            .map {
                mapToFxRateResponse(it)
            }
    }
}

fun mapToFxRateResponse(fxRate: FxRateData): FxRateResponse {

    return FxRateResponse(
        fxRate.tenantId,
        fxRate.bankId,
        fxRate.baseCurrency,
        fxRate.currency,
        fxRate.tier,
        fxRate.directIndirectFlag,
        fxRate.multiplier,
        fxRate.buyRate,
        fxRate.sellRate,
        fxRate.tolerancePercentage,
        fxRate.effectiveDate,
        fxRate.expirationDate,
        fxRate.contractRequirementThreshold
    )
}