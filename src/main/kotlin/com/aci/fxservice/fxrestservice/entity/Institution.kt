package com.aci.fxservice.fxrestservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
//import org.springframework.data.relational.core.mapping.Table
import java.util.Date

@Document(collection = "institution")
data class Institution(
        @Id
        val institutionId: Long,
        val profileid: String,
        val tenantid: String,
        val bankid: String,
        val sourcecurrency: String,
        val targetcurrency: String,
        val initiatedon: Date? = null,
        val settledon: Date? = null,
        val amount: Double,
        val targetamount: Double? = null,
        val rate: Double,
        val status: String,
        val reason: String? = null,
){


}
