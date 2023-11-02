package com.aci.fxservice.fxrestservice.model.request

data class FxRateRequest(
    val tenantId: String,
    val bankId: String,
    val baseCurrency: String,
    val currency: String
)