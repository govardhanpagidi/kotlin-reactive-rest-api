package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.FxRateData
import com.aci.fxservice.fxrestservice.entity.FxRateKey
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FxRateDataRepository : ReactiveMongoRepository<FxRateData, FxRateKey>{
    fun findFxRateDataByTenantIdAndBankIdAndBaseCurrencyAndCurrency(
        tenantId: Int,
        bankId: Int,
        baseCurrency: String,
        currency: String
    ): Flux<FxRateData>
}