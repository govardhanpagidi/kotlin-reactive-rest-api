package com.aci.fxservice.fxrestservice.model.request
data class ConversionRequest (
        val tenantId: String,
        val bankId: String,
        val fromCurrency: String,
        val toCurrency: String,
        val amount: Double,
)