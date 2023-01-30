package com.jphr.lastmarket.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.LifeStyleDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import com.jphr.lastmarket.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "UserInfoService"
class UserInfoService {
    fun getCategory(): MutableLiveData<MutableList<String>> {

        var responseCategory= MutableLiveData<MutableList<String>>()
        val categoryInterface: Call<CategoryDTO> = RetrofitUtil.UserInfoService.getCategory()

        categoryInterface.enqueue(object : Callback<CategoryDTO> {
            override fun onResponse(call: Call<CategoryDTO>, response: Response<CategoryDTO>) {
                val res = response.body()
                Log.d(TAG, "onResponse res 값: $res")
                if(response.code() == 200){
                    if (res != null) {
                        responseCategory.value=res.categories
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryDTO>, t: Throwable) {
                Log.d(TAG, t.message ?: "오류")
            }
        })

        return responseCategory
    }

    fun getLifeStyle():MutableLiveData<MutableList<String>>{

        var responseLifeStyle=MutableLiveData<MutableList<String>>()
        val lifeStyleInterface: Call<LifeStyleDTO> = RetrofitUtil.UserInfoService.getLifeStyle()

        lifeStyleInterface.enqueue(object : Callback<LifeStyleDTO> {
            override fun onResponse(call: Call<LifeStyleDTO>, response: Response<LifeStyleDTO>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        Log.d(TAG, "onResponse: ${res}")
                        responseLifeStyle.value=res.lifestyle
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LifeStyleDTO>, t: Throwable) {
                Log.d(TAG, t.message ?: "오류")
            }
        })

        return responseLifeStyle
    }
    fun insertUserInfo(userInfo:UserInfoDTO) {
        RetrofitUtil.UserInfoService.insertUserInfo(userInfo).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "onResponse: true")
                        true
                    }
                } else {
                    Log.d(TAG, "onResponse:false ")

                    false
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, "onResponse:false ")

            }
        })
    }


}