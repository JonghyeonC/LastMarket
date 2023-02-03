package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.LifeStyleDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserInfoAPI {
    @GET("api/categories")
    fun getCategory(): Call<CategoryDTO>

    @GET("api/lifestyles")
    fun getLifeStyle():Call<LifeStyleDTO>

    @POST("api/user")
    fun insertUserInfo(
        @Header("Authentication") token: String,
        @Body userinfo:UserInfoDTO):Call<Unit>

}