package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.LifeStyleDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInfoAPI {
    @GET("api/user/category")
    fun getCategory(): Call<CategoryDTO>

    @GET("api/user/lifestyle")
    fun getLifeStyle():Call<LifeStyleDTO>

    @POST("api/user")
    fun insertUserInfo(@Body userinfo:UserInfoDTO):Call<Unit>


}