package com.aci.fxservice.fxrestservice.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.util.Date
import org.springframework.data.mongodb.core.mapping.Document
@Document(collection = "fx_rate_data")
data class FxRateData(
        @Id val id:ObjectId,
        val tenantId: String,
        val bankId: String,
        val baseCurrency: String,
        val currency: String,
        val tier: String,
        val directIndirectFlag: String,
        val multiplier: Int?,
        val buyRate: Double,
        val sellRate: Double,
        val tolerancePercentage: Int?,
        val effectiveDate: Date?,
        val expirationDate: Date?,
        val contractRequirementThreshold: String?
)




