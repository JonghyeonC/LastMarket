package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ProductRegisterDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import com.jphr.lastmarket.fragment.SearchFragment
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.util.RetrofitUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductService {
    private val TAG = "ProductService"
    var searchFragment=SearchFragment()


    fun getProduct(word:String?, category: String?, callback: RetrofitCallback<ProductDTO>,issearch:Boolean){
        val productInterface: Call<ProductDTO> = RetrofitUtil.ProductService.getProductList(word,category)
        productInterface.enqueue(object : Callback<ProductDTO> {
            override fun onResponse(call: Call<ProductDTO>, response: Response<ProductDTO>) {
                val res = response.body()
                Log.d(TAG, "onResponse res 값: $res")
                if(response.code() == 200){
                    if (res != null) {
                        callback.onSuccess(response.code(), res,issearch,word,category)
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
    fun getProductWithSort(category: String?,lifestyle:String?,location: String?,sort: String?,dealState: String?,page: String?, callback: RetrofitCallback<ProductDTO>,issearch:Boolean){
        val productInterface: Call<ProductDTO> = RetrofitUtil.ProductService.getProductListWithSort(category, lifestyle,location,sort,dealState,page)
        productInterface.enqueue(object : Callback<ProductDTO> {
            override fun onResponse(call: Call<ProductDTO>, response: Response<ProductDTO>) {
                val res = response.body()
                Log.d(TAG, "onResponse res 값: $res")
                if(response.code() == 200){
                    if (res != null) {
                        callback.onSuccess(response.code(), res,issearch,null,null)
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
//멀티파트(참고) : https://velog.io/@dldmswo1209/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EC%97%90%EC%84%9C-%EC%84%9C%EB%B2%84%EB%A1%9C-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%A0%84%EC%86%A1%ED%95%98%EA%B8%B0
    fun insertProduct(product: RequestBody,images: MutableList<MultipartBody.Part>) {
        RetrofitUtil.ProductService.insertProduct(product,images).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val res = response.body()
                Log.d(TAG, "onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "onResponse insert: ${response.code()}")
                        true
                    }
                } else {
                    Log.d(TAG, "onResponseinsert:false :${response.code()} ")
                    Log.d(TAG, "onResponseinsert: ${response.code()}")

                    false
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onResponseinsert:false ${t.message}")

            }
        })
    }
}