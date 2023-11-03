package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.FxData
import com.aci.fxservice.fxrestservice.logging.ILogger
import com.aci.fxservice.fxrestservice.model.response.FxDataResponse
import com.aci.fxservice.fxrestservice.repository.FxDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class FxDataService(
    private val fxRateDataRepository: FxDataRepository,
    private val logger: ILogger,
) {
    // find all conversions
    fun findFxRates() : Flux<FxDataResponse> {
        logger.logInfo("Received request to find all FxRates")
        // convert to dto after findAll
        return fxRateDataRepository
            .findAll()
            .map {
                mapToFxRateResponse(it)
            }
    }
}

fun mapToFxRateResponse(fxRate: FxData): FxDataResponse {

    return FxDataResponse(
        fxRate.id.tenantId,
        fxRate.id.bankId,
        fxRate.id.baseCurrency,
        fxRate.id.currency,
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