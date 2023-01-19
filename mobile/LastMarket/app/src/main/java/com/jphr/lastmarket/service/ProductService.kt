package com.jphr.lastmarket.service

import android.util.Log
import com.jphr.lastmarket.dto.ProductDTO
import com.jphr.lastmarket.fragment.SearchFragment
import com.jphr.lastmarket.util.RetrofitCallback
import com.jphr.lastmarket.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductService {
    private val TAG = "ProductService"
    var searchFragment=SearchFragment()


    fun getProduct(word:String, category: String?, callback: RetrofitCallback<ProductDTO>){
        val productInterface: Call<ProductDTO> = RetrofitUtil.ProductService.getProductList(word,category)
        productInterface.enqueue(object : Callback<ProductDTO> {
            override fun onResponse(call: Call<ProductDTO>, response: Response<ProductDTO>) {
                val res = response.body()
                Log.d(TAG, "onResponse res 값: $res")
                if(response.code() == 200){
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
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
}