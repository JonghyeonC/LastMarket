package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface MyPageAPI {
    @GET("api/favorite")
    fun getFavoriteList(@Header("Authentication") token: String): Call<MutableList<LikeListProductDTO>>

    @PATCH("api/user/profile")
    fun insertUserProfile(@Header("Authentication") token: String,@Part imgs: MultipartBody.Part): Call<Unit>

    @GET("api/trades/sell")
    fun getSellList():Call<MutableList<TradeDTO>>

    @GET("api/trades/buy")
    fun getBuyList():Call<MutableList<TradeDTO>>

    @GET("api/reviews")
    fun getReviewList():Call<MutableList<ReviewListDTO>>

    @POST("api/reviews")
    fun insertReview(@Header("Authentication") token: String,@Body review:ReviewDTO):Call<Unit>
}