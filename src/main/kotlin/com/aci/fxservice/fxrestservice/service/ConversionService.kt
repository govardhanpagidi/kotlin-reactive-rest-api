package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.Conversion
import com.aci.fxservice.fxrestservice.logging.ILogger
import com.aci.fxservice.fxrestservice.model.response.ConversionResponse
import com.aci.fxservice.fxrestservice.model.request.ConversionRequest
import com.aci.fxservice.fxrestservice.repository.FxRateDataRepository
import com.aci.fxservice.fxrestservice.repository.ConversionRepository
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
    private val exchangeRateRepository: FxRateDataRepository,
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
    fun saveConversion(conversionRequest: ConversionRequest) : Mono<ConversionResponse >{
        logger.logInfo("Received request to save conversion: $conversionRequest")
        // logic to convert the currency
        // get the exchange rates
        val result = exchangeRateRepository.findFxRateDataByBaseCurrencyAndCurrency(conversionRequest.fromCurrency, conversionRequest.toCurrency)
            .switchIfEmpty(
                Mono.error(
                    ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Unable to find exchange rate for ${conversionRequest.fromCurrency} to ${conversionRequest.toCurrency}"
                    )
                )
            )

        return result
            .flatMap { value ->
                    // Process the value and return the resulting Mono
                     val rate = value.buyRate
                logger.logInfo("Rate: $rate")
                    val targetAmount = convertCurrency(
                            conversionRequest.amount,
                            conversionRequest.fromCurrency,
                            conversionRequest.toCurrency,
                            mapOf(Pair(value.baseCurrency, value.currency) to value.buyRate)
                    )

                    conversionRepository.save(
                        Conversion(
                            conversionId = generateRandomLongId(),
                            sourceCurrency = conversionRequest.fromCurrency,
                            targetCurrency = conversionRequest.toCurrency,
                            amount = conversionRequest.amount,
                            targetAmount = targetAmount,
                            rate = rate,
                            initiatedOn = getCurrentEpochTime(),
                            status = "Initiated",
                            reason = "Initiated by user",
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

    // currency conversion logic
    fun convertCurrency(amount: Double, sourceCurrency: String, targetCurrency: String, exchangeRates: Map<Pair<String, String>, Double>): Double {
        val exchangeRate = exchangeRates[Pair(sourceCurrency, targetCurrency)]

        return if (exchangeRate != null) {
            amount * exchangeRate
        } else {
            // If the direct exchange rate isn't available, try the reverse conversion
            val reverseExchangeRate = exchangeRates[Pair(targetCurrency, sourceCurrency)]
            if (reverseExchangeRate != null) {
                amount / reverseExchangeRate
            } else {
                // If exchange rate is not found in either direction, return 0 indicating conversion failure
                logger.logWarn("Unable to find exchange rate for $sourceCurrency to $targetCurrency")
                0.0
            }
        }
    }
}

fun mapToConversionResponse(conversion: Conversion): ConversionResponse {

    return ConversionResponse(
        conversion.conversionId,
        conversion.amount,
        conversion.targetAmount,
        conversion.sourceCurrency,
        conversion.targetCurrency,
        conversion.initiatedOn,
        conversion.rate
    )
}