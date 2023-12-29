package com.example.myapplication.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportRemote(
    val countOrder: Int,
    val totalEarn: Double? = 0.0,
    val avgCheck: Double? = 0.0,
    val serviceList: List<ServiceWithCount>
)