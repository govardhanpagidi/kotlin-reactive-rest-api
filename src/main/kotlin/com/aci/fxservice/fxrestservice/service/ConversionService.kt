package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.Conversion
import com.aci.fxservice.fxrestservice.entity.FxRateKey
import com.aci.fxservice.fxrestservice.logging.ILogger
import com.aci.fxservice.fxrestservice.model.response.ConversionResponse
import com.aci.fxservice.fxrestservice.model.request.ConversionRequest
import com.aci.fxservice.fxrestservice.repository.FxRateDataRepository
import com.aci.fxservice.fxrestservice.repository.ConversionRepository
import com.aci.fxservice.fxrestservice.util.convertCurrency
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.aci.fxservice.fxrestservice.util.generateRandomLongId
import com.aci.fxservice.fxrestservice.util.getCurrentEpochTime
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Service
class ConversionService(
    private val conversionRepository: ConversionRepository,
    private val fxRateDataRepository: FxRateDataRepository,
    private val logger: ILogger,
) {
    // find all conversions
    fun findConversions() : Flux<ConversionResponse> {
        logger.logInfo("Received request to find all conversions")
        // convert to dto after findAll
        return conversionRepository
            .findAll()
            .map {  // convert to dto
                mapToConversionResponse(it)
            }
    }

    // find conversion by id
    fun findConversionById(id: Long) : Mono<ConversionResponse> {
        logger.logInfo("Received request to find conversion by id: $id")
        // convert to dto after find
        return conversionRepository.findById(id)
            .map {
                mapToConversionResponse(it)
            }
    }

    // create conversion
    fun doConversion(conversionRequest: ConversionRequest) : Flux<ConversionResponse >{
        logger.logInfo("Received request to save conversion: $conversionRequest")
        // logic to convert the currency
        // get the exchange rates
        val result = fxRateDataRepository.findFxRateDataByTenantIdAndBankIdAndBaseCurrencyAndCurrency(
            conversionRequest.tenantId,conversionRequest.bankId, conversionRequest.fromCurrency, conversionRequest.toCurrency)
            .switchIfEmpty(
                Mono.error(
                    ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Unable to find exchange rate for ${conversionRequest.fromCurrency} to ${conversionRequest.toCurrency}"
                    )
                )
            ).take(1)

        return result
            .flatMap { value ->
                // Process the value and return the resulting Mono
                val rate = value.buyRate
                logger.logInfo("Rate: $rate")
                val targetAmount = convertCurrency(
                    conversionRequest.amount,
                    conversionRequest.fromCurrency,
                    conversionRequest.toCurrency,
                    mapOf(Pair(conversionRequest.fromCurrency, conversionRequest.toCurrency) to value.buyRate)
                )
                logger.logInfo("Target Amount: $targetAmount")
                conversionRepository.save(
                    Conversion(
                        conversionId = generateRandomLongId(),
                        fromCurrency = conversionRequest.fromCurrency,
                        toCurrency = conversionRequest.toCurrency,
                        amount = conversionRequest.amount,
                        convertedAmount = targetAmount,
                        rate = rate,
                        initiatedOn = getCurrentEpochTime(),
                    ))
                    .map{
                        mapToConversionResponse(it)
                    }
            }
            .onErrorMap { error ->
                logger.logError("error: ${error.message}")
                Exception("Error occurred: ${error.message}")
            }
    }

    fun deleteConversionById(id: Long) : Mono<Void> {
        logger.logInfo("Received request to delete conversion by id: $id")
        return conversionRepository.deleteById(id)
    }

    fun deleteAllConversions() : Mono<Void> {
        logger.logInfo("Received request to delete all conversions")
        return conversionRepository.deleteAll()
    }
}

fun mapToConversionResponse(conversion: Conversion): ConversionResponse {

    return ConversionResponse(
        conversion.conversionId,
        conversion.amount,
        conversion.convertedAmount,
        conversion.fromCurrency,
        conversion.toCurrency,
        conversion.initiatedOn,
        conversion.rate
    )
}