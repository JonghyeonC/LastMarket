package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.JobDTO
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductAPI {
    @GET("/search")
    fun getProductList(
        @Query("word") word:String?=null, @Query("categroy") category:String?=null): Call<ProductDTO>

//    @GET("/user/job")
//    fun getJob(): Call<JobDTO>
//
//    @POST("/user")
//    fun insertUserInfo(@Body userinfo: UserInfoDTO): Call<Unit>

}