package com.aci.fxservice.fxrestservice.entity

import org.springframework.data.annotation.Id
import java.sql.Date
import org.springframework.data.mongodb.core.mapping.Document
@Document(collection = "fxratedata")
data class FxRateData(
        @Id
        val tenantid: String?,  //  Unique and Not Null
        val bankid: String?,   // Unique and Not Null
        val basecurrency: String,
        val tier: String?,
        val currency: String,
        val directflag: Boolean?,
        val multiplier: Int?,
        val buyrate: Double,
        val sellrate: Double,
        val tolerancepercentage: Float?,
        val effectivedate: Date?,
        val expirationdate: Date?,
        val contractRequirementThreshold: String?
)
