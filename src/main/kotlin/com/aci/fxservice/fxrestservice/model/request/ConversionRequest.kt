package com.aci.fxservice.fxrestservice.model.request
data class ConversionRequest (
        val tenantId: Int,
        val bankId: Int,
        val fromCurrency: String,
        val toCurrency: String,
        val amount: Double,
)