package com.jphr.lastmarket.api

import androidx.lifecycle.LiveData
import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.JobDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInfoAPI {
    @GET("/user/category")
    fun getCategory(): Call<CategoryDTO>

    @GET("/user/job")
    fun getJob():Call<JobDTO>

    @POST("/user")
    fun insertUserInfo(@Body userinfo:UserInfoDTO):Call<Unit>


}