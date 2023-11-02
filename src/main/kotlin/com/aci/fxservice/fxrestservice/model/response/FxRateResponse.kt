package com.aci.fxservice.fxrestservice.model.response
import java.util.Date

data class FxRateResponse  (
        val tenantId: String,
        val bankId: String,
        val baseCurrency: String,
        val currency: String,
        val tier: String,
        val directIndirectFlag: String,
        val multiplier: Int?,
        val buyRate: Double,
        val sellRate: Double,
        val tolerancePercentage: Int?,
        val effectiveDate: Date?,
        val expirationDate: Date?,
        val contractRequirementThreshold: String?
)
