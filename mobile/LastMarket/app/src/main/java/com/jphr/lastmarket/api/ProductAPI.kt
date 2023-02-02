package com.jphr.lastmarket.api

import com.jphr.lastmarket.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductAPI {
//    @GET("api/search")
//    fun getProductList(
//        @Query("word") word:String?=null, @Query("categroy") category:String?=null): Call<ProductDTO>

    @GET("api/product")
    fun getProductListWithSort(@Query("category") category:String?=null,
                               @Query("lifestyle") lifestyle:String?=null,
                               @Query("location") location:String?=null,
                               @Query("sort") sort:String?=null,
                               @Query("dealState") dealState:String?=null,
                               @Query("page") page:String?=null,
                               @Query("keyword") word:String?=null): Call<ListDTO>


    @Multipart
    @POST("api/product")
    fun insertProduct(@Part("product") product: RequestBody,
                        @Part imgs: MutableList<MultipartBody.Part>):Call<String>

    @GET("api/product/{productId}")
    fun getProudctDetail(@Path("productId") productId:Long):Call<ProductDetailDTO>

}