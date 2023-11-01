package com.aci.fxservice.fxrestservice.model.request
data class ConversionRequest (
        val sourceCurrency: String,
        val targetCurrency: String,
        val amount: Double,
)