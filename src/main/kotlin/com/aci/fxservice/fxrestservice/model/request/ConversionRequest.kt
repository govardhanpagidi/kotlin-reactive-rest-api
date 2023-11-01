package com.aci.fxservice.fxrestservice.model.request
data class ConversionRequest (
        val fromCurrency: String,
        val toCurrency: String,
        val amount: Double,
)