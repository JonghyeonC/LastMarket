package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.dto.*
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.util.RetrofitUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    fun insertUserProfile(token:String, image: MultipartBody.Part){
        RetrofitUtil.myPageService.insertUserProfile(token,image).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "Detail_onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "Detail_onResponse : ${response.code()}")
                        true
                    }
                } else {
                    Log.d(TAG, "Detail_onResponse:false :${response.code()} ")
                    Log.d(TAG, "onResponse: ${response.code()}")

                    false
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, "onResponse:false ${t.message}")

            }
        })
    }
    fun getSellList(token:String,callback: RetrofitCallback<tradeListDTO>){
        val productInterface: Call<tradeListDTO> = RetrofitUtil.myPageService.getSellList(token,1)
        productInterface.enqueue(object : Callback<tradeListDTO> {
            override fun onResponse(call: Call<tradeListDTO>, response: Response<tradeListDTO>) {
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

            override fun onFailure(call: Call<tradeListDTO>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
    fun getBuyList(token:String,callback: RetrofitCallback<tradeListDTO>){
        val productInterface: Call<tradeListDTO> = RetrofitUtil.myPageService.getBuyList(token,1)
        productInterface.enqueue(object : Callback<tradeListDTO> {
            override fun onResponse(call: Call<tradeListDTO>, response: Response<tradeListDTO>) {
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

            override fun onFailure(call: Call<tradeListDTO>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
    fun getReviewList(callback: RetrofitCallback<MutableList<ReviewListDTO>>){
        val productInterface: Call<MutableList<ReviewListDTO>> = RetrofitUtil.myPageService.getReviewList()
        productInterface.enqueue(object : Callback<MutableList<ReviewListDTO>> {
            override fun onResponse(call: Call<MutableList<ReviewListDTO>>, response: Response<MutableList<ReviewListDTO>>) {
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

            override fun onFailure(call: Call<MutableList<ReviewListDTO>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
    fun insertReview(token:String,review:ReviewDTO) {
        RetrofitUtil.myPageService.insertReview(token,review).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "insertData: ${response}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "insertdata: true")
                        true
                    }
                } else {
                    Log.d(TAG, "insertdata:false ")

                    false
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, "insertdata:false ")

            }
        })
    }
}