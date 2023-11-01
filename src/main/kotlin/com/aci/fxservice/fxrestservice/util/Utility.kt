package com.aci.fxservice.fxrestservice.util

import kotlin.random.Random

fun generateRandomLongId(): Long {
    return Random.nextLong(Long.MAX_VALUE)
}