package com.jphr.lastmarket.dto

data class UserInfoDTO(
    val addr: String,
    val categories: MutableList<String>,
    val lifeStyle:String,
    val nickname: String
)
