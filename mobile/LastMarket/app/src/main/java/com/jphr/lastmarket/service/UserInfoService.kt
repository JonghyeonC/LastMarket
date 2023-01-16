package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "UserInfoService"
class UserInfoService {
    fun getCategory():MutableList<String>{

        val responseCategory: MutableList<String> = mutableListOf()
        val categoryInterface: Call<MutableList<String>> = RetrofitUtil.UserInfoService.getCategory()

        categoryInterface.enqueue(object : Callback<MutableList<String>> {
            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        responseCategory.add(res.toString())
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Log.d(TAG, t.message ?: "오류")
            }
        })

        return responseCategory
    }

    fun getJob():List<String>{

        val responseJob: MutableList<String> = mutableListOf()
        val jobInterface: Call<MutableList<String>> = RetrofitUtil.UserInfoService.getJob()

        jobInterface.enqueue(object : Callback<MutableList<String>> {
            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        responseJob.add(res.toString())
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Log.d(TAG, t.message ?: "오류")
            }
        })

        return responseJob
    }

}