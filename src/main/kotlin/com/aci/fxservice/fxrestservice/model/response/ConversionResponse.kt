package com.aci.fxservice.fxrestservice.model.response
data class ConversionResponse  (
        var id : Long? = null,
        val amount: Double,
        val convertedAmount: Double? = null,
        val fromCurrency: String,
        val toCurrency: String,
        val initiatedOn: Long? = null,
        val rate: Double,
)