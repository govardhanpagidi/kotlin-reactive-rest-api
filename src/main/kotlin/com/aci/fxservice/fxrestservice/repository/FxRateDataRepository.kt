package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.FxRateData
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono

interface FxRateDataRepository : ReactiveMongoRepository<FxRateData, Long>{
    fun findFxRateDataByBaseCurrencyAndCurrency(baseCurrency: String, currency: String): Mono<FxRateData>
}