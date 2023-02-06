package com.jphr.lastmarket.dto


data class ProductRegisterDTO(
    val category: String,
    val content: String,
    val instantPrice: Long,
    val lifestyle: String,
    val liveTime: String?,
    val startingPrice: Long?,
    val title: String
)