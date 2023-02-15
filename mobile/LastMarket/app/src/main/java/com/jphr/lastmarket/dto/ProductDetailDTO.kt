package com.jphr.lastmarket.dto

import java.time.LocalDateTime

data class ProductDetailDTO(
    val content: String,
    val createdDateTime: String?,
    val dealState: String,
    val favoriteCnt: Long,
    val imgURIs: MutableList<String>,
    val instantPrice: Long,
    val favorite: Boolean,
    val lifestyle: String,
    val liveTime: String?,
    val location: String,
    val productId: Long,
    val profile: String,
    val sellerId: Long,
    val sellerNickname: String,
    val startingPrice: Long?,
    val title: String
):java.io.Serializable