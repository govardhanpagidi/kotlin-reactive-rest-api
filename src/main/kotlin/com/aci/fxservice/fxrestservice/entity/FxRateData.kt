package com.aci.fxservice.fxrestservice.entity

import org.springframework.data.annotation.Id
import java.sql.Date

data class FxRateData(
        @Id val id : Long? = null,
        val tenantId: String,  //  Unique and Not Null
        val bankId: String,   // Unique and Not Null
        val baseCurrency: String,
        val tier: String?,
        val currency: String,
        val directFlag: Boolean,
        val multiplier: Int?,
        val buyRate: Double,
        val sellRate: Double,
        val tolerancePercentage: Float?,
        val effectiveDate: Date?,
        val expirationDate: Date?,
        val contractRequirementThreshold: String?
)
