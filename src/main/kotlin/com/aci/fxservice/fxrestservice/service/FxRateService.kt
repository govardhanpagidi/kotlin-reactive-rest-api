package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.FxRateData
import com.aci.fxservice.fxrestservice.logging.ILogger
import com.aci.fxservice.fxrestservice.model.request.FxRateByIDRequest
import com.aci.fxservice.fxrestservice.model.request.FxRateRequest
import com.aci.fxservice.fxrestservice.model.response.FxRateResponse
import com.aci.fxservice.fxrestservice.repository.FxRateDataRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
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
    fun findFxRateById(request: FxRateByIDRequest) : Flux<FxRateResponse> {
        logger.logInfo("Received request to find FxRate by id: $request")
        // convert to dto after find
        return findEntryById(request).take(1).map {
            mapToFxRateResponse(it)
        }
    }

    fun saveFxRate(request: FxRateRequest): Mono<FxRateResponse> {
        val fxSaveRate = fxRateFromRequest(request)
        logger.logInfo("Received request to save FxRate: $request")
        return fxRateDataRepository.save(fxSaveRate)
        .map {
            mapToFxRateResponse(it)
        }
    }

    fun deleteFxRate(request: FxRateByIDRequest): Flux<Void> {
        logger.logInfo("Received request to delete FxRate: $request")
       return findEntryById(request).flatMap {
            if (it != null){
                logger.logInfo("found record delete FxRate: $request")
                fxRateDataRepository.delete(it)
            }else{
                logger.logInfo("no record found to delete FxRate: $request")
                Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find FxRate for ${request.tenantId} ${request.bankId} ${request.baseCurrency} ${request.currency}"))
            }
        }
    }

    fun findEntryById(request : FxRateByIDRequest): Flux<FxRateData> {
        return fxRateDataRepository.findFxRateDataByTenantIdAndBankIdAndBaseCurrencyAndCurrency(
            request.tenantId,
            request.bankId,
            request.baseCurrency,
            request.currency
        ).switchIfEmpty(
            Flux.error(
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unable to find exchange rate for ${request.baseCurrency} to ${request.currency}"
                )
            )
        )
    }

    private fun fxRateFromRequest(request: FxRateRequest): FxRateData {
        val fxSaveRate = FxRateData(
            null,
            request.tenantId,
            request.bankId,
            request.baseCurrency,
            request.currency,
            request.tier,
            request.directIndirectFlag,
            request.multiplier,
            request.buyRate,
            request.sellRate,
            request.tolerancePercentage,
            request.effectiveDate,
            request.expirationDate,
            request.contractRequirementThreshold
        )
        return fxSaveRate
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