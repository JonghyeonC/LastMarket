package com.jphr.lastmarket.dto

import java.time.LocalDateTime

data class ProductDetailDTO(
    val content: String,
    val createdDateTime: LocalDateTime,
    val dealState: String,
    val favoriteCnt: Long,
    val imgURIs: MutableList<String>,
    val instantPrice: Long,
    val isFavorite: Boolean,
    val lifestyle: String,
    val liveTime: LocalDateTime,
    val location: String,
    val productId: Long,
    val profile: String,
    val sellerId: Long,
    val sellerNickname: String,
    val startingPrice: Long,
    val title: String
)