package com.jphr.lastmarket.dto

data class ProductRegisterDTO(
    val category: String,
    val content: String,
    val instantPrice: String,
    val lifestyle: String,
    val liveTime: String="",
    val startingPrice: String="",
    val title: String
)