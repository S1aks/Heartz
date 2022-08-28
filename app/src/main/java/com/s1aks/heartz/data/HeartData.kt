package com.s1aks.heartz.data

import java.time.LocalDateTime

data class HeartData(
    val id: Int,
    val time: LocalDateTime,
    val topPressure: Int,
    val lowerPressure: Int,
    val pulse: Int
)
