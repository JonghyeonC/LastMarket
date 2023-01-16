package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.UserInfoDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInfoAPI {
    @GET("/user/category")
    fun getCategory(): Call<MutableList<String>>

    @GET("/user/job")
    fun getJob():Call<MutableList<String>>

    @POST("/user")
    fun insertUserInfo():Call<UserInfoDTO>


}