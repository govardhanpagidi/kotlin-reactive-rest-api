package com.aci.fxservice.fxrestservice.model.response
data class ConversionResponse  (
        var id : Long? = null,
        val actualAmount: Double,
        val convertedAmount: Double? = null,
        val sourceCurrency: String,
        val targetCurrency: String,
        val initiatedOn: Long? = null,
        val rate: Double,
)