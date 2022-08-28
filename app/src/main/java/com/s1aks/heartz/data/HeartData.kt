package com.s1aks.heartz.data

data class HeartData(
    var type: Type? = null,
    var id: String? = null,
    val time: String? = null,
    val topPressure: Int? = null,
    val lowPressure: Int? = null,
    val pulse: Int? = null
) {
    enum class Type {
        DATE, INFO
    }
}
