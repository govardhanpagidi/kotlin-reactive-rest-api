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
        return exchangeRateRepository.findFxRateDataByBasecurrencyAndCurrency(institutionRequest.sourceCurrency, institutionRequest.targetCurrency)
            .doOnNext { value ->
                // Log or print the data emitted by findFxRateDataByBaseCurrencyAndCurrency
                logger.logInfo("Received value: $value")
            }
            .flatMap { value ->
                    // Process the value and return the resulting Mono
                     val rate = value.buyrate
                logger.logInfo("Rate: $rate")
                    val targetAmount = convertCurrency(
                            institutionRequest.amount,
                            institutionRequest.sourceCurrency,
                            institutionRequest.targetCurrency,
                            mapOf(Pair(value.basecurrency, value.currency) to value.buyrate)
                    )

                    institutionRepository.save(
                        Institution(
                            institutionId = generateRandomLongId(),
                            profileid = institutionRequest.profileId,
                            tenantid = institutionRequest.tenantId,
                            bankid = institutionRequest.bankId,
                            sourcecurrency = institutionRequest.sourceCurrency,
                            targetcurrency = institutionRequest.targetCurrency,
                            amount = institutionRequest.amount,
                            targetamount = targetAmount,
                            rate = rate,
                            initiatedon = getCurrentEpochTime(),
                            status = "Initiated",
                            reason = "Initiated by user",
                        ))
                        .map{
                            mapToInstitutionResponse(it)
                        }
                }
                .onErrorMap { error ->
                    // TODO: Log the error
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
        generateRandomLongId(),
        institution.amount,
        institution.targetamount,
        institution.sourcecurrency,
        institution.targetcurrency,
        institution.initiatedon,
        institution.rate
    )
}