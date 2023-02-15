package com.jphr.lastmarket.dto

import java.time.LocalDateTime

data class Product(
    val createdDateTime: String,
    val dealState: String,
    val favoriteCnt: Long,
    val imgURI: String,
    val instantPrice: Long,
    val isFavorite: Boolean,
    val liveTime: String?,
    val location: String,
    val productId: Long,
    val sellerId: Long,
    val sellerNickname: String,
    val startingPrice: Long,
    val title: String
)