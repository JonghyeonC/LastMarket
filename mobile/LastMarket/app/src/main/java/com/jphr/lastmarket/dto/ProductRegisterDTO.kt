package com.jphr.lastmarket.dto

import java.time.LocalDateTime

data class ProductRegisterDTO(
    val category: String,
    val content: String,
    val instantPrice: Long,
    val lifestyle: String,
    val liveTime: LocalDateTime?,
    val startingPrice: Long?,
    val title: String
)