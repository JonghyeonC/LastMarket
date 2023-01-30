package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.LifeStyleDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInfoAPI {
    @GET("/user/category")
    fun getCategory(): Call<CategoryDTO>

    @GET("/user/lifestyle")
    fun getLifeStyle():Call<LifeStyleDTO>

    @POST("/user")
    fun insertUserInfo(@Body userinfo:UserInfoDTO):Call<Unit>


}