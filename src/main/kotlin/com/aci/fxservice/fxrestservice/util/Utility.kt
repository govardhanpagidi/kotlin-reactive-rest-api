package com.aci.fxservice.fxrestservice.util

import kotlin.random.Random

fun generateRandomLongId(): Long {
    return Random.nextLong(Long.MAX_VALUE)
}

fun getCurrentEpochTime() : Long{
    return System.currentTimeMillis();
}

fun convertCurrency(amount: Double, sourceCurrency: String, targetCurrency: String, exchangeRates: Map<Pair<String, String>, Double>): Double {
    val exchangeRate = exchangeRates[Pair(sourceCurrency, targetCurrency)]

    return if (exchangeRate != null) {
        amount * exchangeRate
    } else {
        // If the direct exchange rate isn't available, try the reverse conversion
        val reverseExchangeRate = exchangeRates[Pair(targetCurrency, sourceCurrency)]
        if (reverseExchangeRate != null) {
            amount / reverseExchangeRate
        } else {
            // If exchange rate is not found in either direction, return 0 indicating conversion failure
            0.0
        }
    }
}