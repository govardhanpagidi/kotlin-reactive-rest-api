package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.Institution
import com.aci.fxservice.fxrestservice.model.dto.InstitutionResponse
import com.aci.fxservice.fxrestservice.model.request.InstitutionRequest
import com.aci.fxservice.fxrestservice.repository.FxRateDataRepository
import com.aci.fxservice.fxrestservice.repository.InstitutionRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.aci.fxservice.fxrestservice.util.generateRandomLongId

@Service
class InstitutionService(
        private val institutionRepository: InstitutionRepository,
        private val exchangeRateRepository: FxRateDataRepository,
) {
    // find all institutions
    fun findAll() : Flux<InstitutionResponse> {
        // convert to dto after findAll
        return institutionRepository
                .findAll()
                .map {
                    institution -> InstitutionResponse(
                        generateRandomLongId(),
                        institution.profileid,
                        institution.tenantid,
                        institution.bankid,
                        institution.sourcecurrency,
                        institution.targetcurrency,
                        institution.initiatedon,
                        institution.settledon,
                        institution.amount,
                        institution.targetamount,
                        institution.rate,
                       institution.status,
                        institution.reason
                    )
                }
    }

    // find institution by id
    fun findById(id: Long) : Mono<InstitutionResponse> {
        // convert to dto after find
        return institutionRepository.findById(id)
                .map {
                    institution -> InstitutionResponse(
                        institution.institutionId,
                        institution.profileid,
                        institution.tenantid,
                        institution.bankid,
                        institution.sourcecurrency,
                        institution.targetcurrency,
                        institution.initiatedon,
                        institution.settledon,
                        institution.amount,
                        institution.targetamount,
                        institution.rate,
                        institution.status,
                        institution.reason
                    )
                }
    }

    // create institution
    fun save(institutionRequest: InstitutionRequest) : Mono<InstitutionResponse >{

        // logic to convert the currency
        // get the exchange rates
        return exchangeRateRepository.findFxRateDataByBasecurrencyAndCurrency(institutionRequest.sourceCurrency, institutionRequest.targetCurrency)
            .doOnNext { value ->
                // Log or print the data emitted by findFxRateDataByBaseCurrencyAndCurrency
                println("Received value: $value")
            }
            .flatMap { value ->
                    // Process the value and return the resulting Mono
                     val rate = value.buyrate
                    println("Rate: $rate")
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
                            status = "Initiated",
                            reason = "Initiated by user",
                        ))
                        .map{ institution ->
                            InstitutionResponse(
                                institution.institutionId,
                                institution.profileid,
                                institution.tenantid,
                                institution.bankid,
                                institution.sourcecurrency,
                                institution.targetcurrency,
                                institution.initiatedon,
                                institution.settledon,
                                institution.amount,
                                institution.targetamount,
                                institution.rate,
                                institution.status,
                                institution.reason
                            )
                        }
                }
                .onErrorMap { error ->
                    Exception("Error occurred: ${error.message}")
                }

    }

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
                0.0
            }
        }
    }
}