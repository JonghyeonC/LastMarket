package com.jphr.lastmarket.dto

data class UserInfoDTO(
    val addr: String,
    val categories: List<String>,
    val job: String,
    val nickname: String
)