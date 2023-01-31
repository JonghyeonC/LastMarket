package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.dto.ProductRegisterDTO
import com.jphr.lastmarket.dto.UserInfoDTO
import com.jphr.lastmarket.fragment.SearchFragment
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.util.RetrofitUtil
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

    fun insertProduct(product: ProductRegisterDTO) {
        RetrofitUtil.ProductService.insertProduct(product).enqueue(object : Callback<ProductRegisterDTO> {
            override fun onResponse(call: Call<ProductRegisterDTO>, response: Response<ProductRegisterDTO>) {
                val res = response.body()
                Log.d(TAG, "onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "onResponse: ${response.code()}")
                        true
                    }
                } else {
                    Log.d(TAG, "onResponse:false ")
                    Log.d(TAG, "onResponse: ${response.code()}")

                    false
                }
            }
            override fun onFailure(call: Call<ProductRegisterDTO>, t: Throwable) {
                Log.d(TAG, "onResponse:false ")

            }
        })
    }
}