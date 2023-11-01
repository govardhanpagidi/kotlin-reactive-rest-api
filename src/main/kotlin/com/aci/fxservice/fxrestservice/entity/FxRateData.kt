package com.aci.fxservice.fxrestservice.entity

import org.springframework.data.annotation.Id
import java.sql.Date
import org.springframework.data.mongodb.core.mapping.Document
@Document(collection = "fxratedata")
data class FxRateData(
        @Id
        val tenantId: String?,  //  Unique and Not Null
        val bankId: String?,   // Unique and Not Null
        val baseCurrency: String,
        val tier: String?,
        val currency: String,
        val directFlag: Boolean?,
        val multiplier: Int?,
        val buyRate: Double,
        val sellRate: Double,
        val contractRequirementThreshold: String?
)

