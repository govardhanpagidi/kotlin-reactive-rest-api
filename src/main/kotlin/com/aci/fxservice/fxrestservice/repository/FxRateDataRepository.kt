package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.FxRateData
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

interface FxRateDataRepository : ReactiveMongoRepository<FxRateData, Long>{

    fun findFxRateDataByBasecurrencyAndCurrency(baseCurrency: String, currency: String): Mono<FxRateData>
}