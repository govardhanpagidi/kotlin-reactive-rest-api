package com.aci.fxservice.fxrestservice.entity

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class FxRateKey(
    val tenantId: Int,
    val bankId: Int,
    val baseCurrency: String,
    val currency: String
)