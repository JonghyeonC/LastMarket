package com.jphr.lastmarket.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jphr.lastmarket.dto.CategoryDTO
import com.jphr.lastmarket.dto.JobDTO
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

    fun getJob():MutableLiveData<MutableList<String>>{

        var responseJob=MutableLiveData<MutableList<String>>()
        val jobInterface: Call<JobDTO> = RetrofitUtil.UserInfoService.getJob()

        jobInterface.enqueue(object : Callback<JobDTO> {
            override fun onResponse(call: Call<JobDTO>, response: Response<JobDTO>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        responseJob.value=res.jobs
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