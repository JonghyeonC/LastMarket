package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.LikeListProductDTO
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ReviewListDTO
import com.jphr.lastmarket.dto.SellListDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Part

interface MyPageAPI {
    @GET("api/favorite")
    fun getFavoriteList(@Header("Authentication") token: String): Call<MutableList<LikeListProductDTO>>

    @PATCH("api/user/profile")
    fun insertUserProfile(@Header("Authentication") token: String,@Part imgs: MultipartBody.Part): Call<Unit>

    @GET("api/trades/sell")
    fun getSellList():Call<MutableList<SellListDTO>>

    @GET("api/reviews")
    fun getReviewList():Call<MutableList<ReviewListDTO>>

}