package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.Institution
import com.aci.fxservice.fxrestservice.logging.ILogger
import com.aci.fxservice.fxrestservice.model.response.InstitutionResponse
import com.aci.fxservice.fxrestservice.model.request.InstitutionRequest
import com.aci.fxservice.fxrestservice.repository.FxRateDataRepository
import com.aci.fxservice.fxrestservice.repository.InstitutionRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.aci.fxservice.fxrestservice.util.generateRandomLongId
import com.aci.fxservice.fxrestservice.util.getCurrentEpochTime
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Service
class InstitutionService(
    private val institutionRepository: InstitutionRepository,
    private val exchangeRateRepository: FxRateDataRepository,
    private val logger: ILogger,
) {
    // find all institutions
    fun findInstitutions() : Flux<InstitutionResponse> {
        logger.logInfo("Received request to find all institutions")
        // convert to dto after findAll
        return institutionRepository
                .findAll()
                .map {  // convert to dto
                    mapToInstitutionResponse(it)
                }
    }

    // find institution by id
    fun findInstitutionById(id: Long) : Mono<InstitutionResponse> {
        logger.logInfo("Received request to find institution by id: $id")
        // convert to dto after find
        return institutionRepository.findById(id)
                .map {
                    mapToInstitutionResponse(it)
                }
    }

    // create institution
    fun saveInstitution(institutionRequest: InstitutionRequest) : Mono<InstitutionResponse >{
        logger.logInfo("Received request to save institution: $institutionRequest")
        // logic to convert the currency
        // get the exchange rates
        val result = exchangeRateRepository.findFxRateDataByBaseCurrencyAndCurrency(institutionRequest.sourceCurrency, institutionRequest.targetCurrency)
            .switchIfEmpty(
                Mono.error(
                    ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Unable to find exchange rate for ${institutionRequest.sourceCurrency} to ${institutionRequest.targetCurrency}"
                    )
                )
            )

        return result
            .flatMap { value ->
                    // Process the value and return the resulting Mono
                     val rate = value.buyRate
                logger.logInfo("Rate: $rate")
                    val targetAmount = convertCurrency(
                            institutionRequest.amount,
                            institutionRequest.sourceCurrency,
                            institutionRequest.targetCurrency,
                            mapOf(Pair(value.baseCurrency, value.currency) to value.buyRate)
                    )

                    institutionRepository.save(
                        Institution(
                            institutionId = generateRandomLongId(),
                            sourceCurrency = institutionRequest.sourceCurrency,
                            targetCurrency = institutionRequest.targetCurrency,
                            amount = institutionRequest.amount,
                            targetAmount = targetAmount,
                            rate = rate,
                            initiatedOn = getCurrentEpochTime(),
                            status = "Initiated",
                            reason = "Initiated by user",
                        ))
                        .map{
                            mapToInstitutionResponse(it)
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
fun mapToInstitutionResponse(institution: Institution): InstitutionResponse {

    return InstitutionResponse(
        institution.institutionId,
        institution.amount,
        institution.targetAmount,
        institution.sourceCurrency,
        institution.targetCurrency,
        institution.initiatedOn,
        institution.rate
    )
}