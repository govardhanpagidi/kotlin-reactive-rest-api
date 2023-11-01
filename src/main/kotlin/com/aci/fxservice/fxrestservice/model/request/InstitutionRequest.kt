package com.aci.fxservice.fxrestservice.model.request
data class InstitutionRequest (
        val profileId: String,
        val tenantId: String,
        val bankId: String,
        val sourceCurrency: String,
        val targetCurrency: String,
        val amount: Double,
)