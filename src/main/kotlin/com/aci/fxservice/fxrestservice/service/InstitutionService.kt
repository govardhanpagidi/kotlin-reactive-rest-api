package com.aci.fxservice.fxrestservice.service

import com.aci.fxservice.fxrestservice.entity.FxRateData
import com.aci.fxservice.fxrestservice.repository.InstitutionRepository
import com.aci.fxservice.fxrestservice.entity.Institution
import com.aci.fxservice.fxrestservice.model.dto.InstitutionResponse
import com.aci.fxservice.fxrestservice.model.request.InstitutionRequest
import com.aci.fxservice.fxrestservice.repository.FxRateDataRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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
                        institution.id,
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
                        institution.id,
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

    fun findInstitutionById(id: Long) : Mono<Institution> {
        // convert to dto after find
        return institutionRepository.findById(id)
    }

    // create institution
    fun save(institutionRequest: InstitutionRequest) : Mono<InstitutionResponse >{

        // logic to convert the currency
         var targetAmount = 0.0
         var rate = 0.0
        // get the exchange rates
        exchangeRateRepository.findByFxRateDataBySourceAndTargetCurrency(institutionRequest.sourceCurrency, institutionRequest.targetCurrency)
                .flatMap { value ->
                    // Process the value and return the resulting Mono
                     rate = value.buyRate
                    System.out.println("Rate: ${rate}")
                    targetAmount = convertCurrency(
                            institutionRequest.amount,
                            institutionRequest.sourceCurrency,
                            institutionRequest.targetCurrency,
                            mapOf(Pair(value.baseCurrency, value.currency) to value.buyRate)
                    )
                    Mono.just(targetAmount) // or return the actual value
                }
                .onErrorResume { error ->
                    // Handle errors if they occur during the process
                    println("Error: ${error.message}")
                    Mono.empty() // Return an empty Mono or a default value if needed
                }

        //
        return institutionRepository.save(
                Institution(
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
                        institution.id,
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