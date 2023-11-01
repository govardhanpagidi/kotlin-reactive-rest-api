package com.aci.fxservice.fxrestservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
//import org.springframework.data.relational.core.mapping.Table

@Document
data class Conversion(
        @Id
        val conversionId: Long,
        val sourceCurrency: String,
        val targetCurrency: String,
        val initiatedOn: Long? = null,
        val settledCn: Long? = null,
        val amount: Double,
        val targetAmount: Double? = null,
        val rate: Double,
        val status: String,
        val reason: String? = null,
){


}
