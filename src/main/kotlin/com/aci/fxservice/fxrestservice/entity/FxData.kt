package com.aci.fxservice.fxrestservice.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.util.Date
import org.springframework.data.mongodb.core.mapping.Document
@Document(collection = "fx_rate_data")
data class FxData(
        @Id val id:FxRateKey,
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




