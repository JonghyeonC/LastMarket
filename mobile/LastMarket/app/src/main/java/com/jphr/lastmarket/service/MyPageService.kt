package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.dto.LikeListProductDTO
import com.jphr.lastmarket.dto.ListDTO
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MyPageService"
class MyPageService {
    fun getLikeList(token:String,callback: RetrofitCallback<MutableList<LikeListProductDTO>>){

        val productInterface: Call<MutableList<LikeListProductDTO>> = RetrofitUtil.myPageService.getFavoriteList(token)
        productInterface.enqueue(object : Callback<MutableList<LikeListProductDTO>> {
            override fun onResponse(call: Call<MutableList<LikeListProductDTO>>, response: Response<MutableList<LikeListProductDTO>>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        Log.d(TAG, "onResponse: ${response}")
                        callback.onSuccess(response.code(), res,false,null,null)

                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: ${response}")
                    Log.d(TAG, "onResponse: ${response.body()}")
                    Log.d(TAG, "onResponse: ${response.errorBody()}")

                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<MutableList<LikeListProductDTO>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}