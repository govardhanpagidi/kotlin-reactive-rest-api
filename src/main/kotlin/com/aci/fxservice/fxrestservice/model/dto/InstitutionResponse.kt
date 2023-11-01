package com.aci.fxservice.fxrestservice.model.dto

import java.util.Date


data class InstitutionResponse  (
        var id : Long? = null,
        val profileid: String,
        val tenantid: String,
        val bankid: String,
        val sourcecurrency: String,
        val targetcurrency: String,
        val initiatedon: Date? = null,
        val settledon: Date? = null,
        val amount: Double,
        val targetAmount: Double? = null,
        val rate: Double,
        val status: String,
        val reason: String? = null,
)