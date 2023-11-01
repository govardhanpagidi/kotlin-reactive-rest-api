package com.aci.fxservice.fxrestservice.repository

import com.aci.fxservice.fxrestservice.entity.FxRateData
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface FxRateDataRepository : ReactiveCrudRepository<FxRateData, Long>{

    @Query("SELECT * FROM Fx_Rate_Data WHERE Fx_Rate_Data.baseCurrency = :sourceCurrency AND currency = :targetCurrency")
    fun findByFxRateDataBySourceAndTargetCurrency(sourceCurrency: String, targetCurrency: String): Mono<FxRateData>
}