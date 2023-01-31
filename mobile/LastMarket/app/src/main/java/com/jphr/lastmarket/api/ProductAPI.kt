package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ProductRegisterDTO
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

    @GET("/products")
    fun getProductListWithSort(@Query("category") category:String?=null,
                               @Query("lifestyle") lifestyle:String?=null,
                               @Query("location") location:String?=null,
                               @Query("sort") sort:String?=null,
                               @Query("dealState") dealState:String?=null,
                               @Query("page") page:String?=null): Call<ProductDTO>

    @POST("/product")
    fun insertProduct(@Body product: ProductRegisterDTO):Call<ProductRegisterDTO>
}