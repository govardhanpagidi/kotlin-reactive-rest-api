package com.aci.fxservice.fxrestservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
//import org.springframework.data.relational.core.mapping.Table

@Document
data class Conversion(
        @Id
        val conversionId: Long,
        val fromCurrency: String,
        val toCurrency: String,
        val initiatedOn: Long? = null,
        val amount: Double,
        val convertedAmount: Double? = null,
        val rate: Double,
){


}
