package com.aci.fxservice.fxrestservice.entity

data class FxRateKey(
    val tenantId: Int,
    val bankId: Int,
    val baseCurrency: String,
    val currency: String
)