package com.aci.fxservice.fxrestservice.model.request

data class FxRateRequest(
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


data class FxRateByIDRequest(
    val tenantId: Int,
    val bankId: Int,
    val baseCurrency: String,
    val currency: String,
)