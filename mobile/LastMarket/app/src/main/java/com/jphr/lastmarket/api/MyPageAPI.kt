package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.LikeListProductDTO
import com.jphr.lastmarket.dto.ProductDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageAPI {
    @GET("api/favorite")
    fun getFavoriteList(@Header("Authentication") token: String): Call<MutableList<LikeListProductDTO>>



}