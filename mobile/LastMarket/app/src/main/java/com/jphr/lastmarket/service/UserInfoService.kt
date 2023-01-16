package com.jphr.lastmarket.service

import android.util.Log
import androidx.lifecycle.LiveData
import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.JobDTO
import com.jphr.lastmarket.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "UserInfoService"
class UserInfoService {
    fun getCategory():LiveData<MutableList<String>>{

        var responseCategory= mutableListOf<String>()
        val categoryInterface: Call<CategoryDTO> = RetrofitUtil.UserInfoService.getCategory()

        categoryInterface.enqueue(object : Callback<CategoryDTO> {
            override fun onResponse(call: Call<CategoryDTO>, response: Response<LiveData<CategoryDTO>>) {
                val res = response.body()
                Log.d(TAG, "onResponse res 값: $res")
                if(response.code() == 200){
                    if (res != null) {
                        responseCategory=res
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

    fun getJob():MutableList<String>{

        var responseJob: MutableList<String> = mutableListOf()
        val jobInterface: Call<JobDTO> = RetrofitUtil.UserInfoService.getJob()

        jobInterface.enqueue(object : Callback<JobDTO> {
            override fun onResponse(call: Call<JobDTO>, response: Response<JobDTO>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        responseJob=res.jobs
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JobDTO>, t: Throwable) {
                Log.d(TAG, t.message ?: "오류")
            }
        })

        return responseJob
    }

}