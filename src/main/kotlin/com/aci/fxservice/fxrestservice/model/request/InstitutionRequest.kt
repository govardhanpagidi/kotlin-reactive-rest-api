package com.aci.fxservice.fxrestservice.model.request

import java.sql.Date

data class InstitutionRequest (
        val profileId: String,
        val tenantId: String,
        val bankId: String,
        val sourceCurrency: String,
        val targetCurrency: String,
        val amount: Double,
)