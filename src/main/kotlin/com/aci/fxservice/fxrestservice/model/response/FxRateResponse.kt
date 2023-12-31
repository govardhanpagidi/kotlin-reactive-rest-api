package com.aci.fxservice.fxrestservice.model.response
import java.util.Date

data class FxRateResponse  (
        val tenantId: Int,
        val bankId: Int,
        val baseCurrency: String,
        val currency: String,
        val tier: String,
        val directIndirectFlag: String,
        val multiplier: Int?,
        val buyRate: Double,
        val sellRate: Double,
        val tolerancePercentage: Int?,
        val effectiveDate: String?,
        val expirationDate: String?,
        val contractRequirementThreshold: String?
)
