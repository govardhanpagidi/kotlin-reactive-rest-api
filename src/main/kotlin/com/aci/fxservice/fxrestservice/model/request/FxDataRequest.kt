package com.aci.fxservice.fxrestservice.model.request

data class FxDataRequest(
    val tenantId: String,
    val bankId: String,
    val baseCurrency: String,
    val currency: String
)