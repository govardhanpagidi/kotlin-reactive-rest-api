package com.aci.fxservice.fxrestservice.model.request
data class InstitutionRequest (
        val sourceCurrency: String,
        val targetCurrency: String,
        val amount: Double,
)