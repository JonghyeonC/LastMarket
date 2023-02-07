package com.jphr.lastmarket.service

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.jphr.lastmarket.activity.MainActivity
import com.jphr.lastmarket.dto.*
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


//    fun getProduct(word:String?, category: String?, callback: RetrofitCallback<ProductDTO>,issearch:Boolean){
//        val productInterface: Call<ProductDTO> = RetrofitUtil.ProductService.getProductList(word,category)
//        productInterface.enqueue(object : Callback<ProductDTO> {
//            override fun onResponse(call: Call<ProductDTO>, response: Response<ProductDTO>) {
//                val res = response.body()
//                Log.d(TAG, "onResponse res 값: $res")
//                if(response.code() == 200){
//                    if (res != null) {
//                        callback.onSuccess(response.code(), res,issearch,word,category)
//                    }
//                    Log.d(TAG, "onResponse: $res")
//                } else {
//                    callback.onFailure(response.code())
//                }
//            }
//
//            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
//                callback.onError(t)
//            }
//        })
//    }
    fun getProductWithSort(
    category: String?,
    lifestyle:String?,
    location: String?,
    sort: String?,
    dealState: String?,
    page: String?, callback:RetrofitCallback<ListDTO>,
    issearch:Boolean,
    word: String?){
       
        val productInterface: Call<ListDTO> = RetrofitUtil.ProductService.getProductListWithSort(category, lifestyle,location,sort,dealState,page,word)
        productInterface.enqueue(object : Callback<ListDTO> {
            override fun onResponse(call: Call<ListDTO>, response: Response<ListDTO>) {
                val res = response.body()
                Log.d(TAG, "category: $category")
                Log.d(TAG, "onResponse res 값: $res")
                if(response.code() == 200){
                    if (res != null) {
                        Log.d(TAG, "onResponse: ${response}")
                        callback.onSuccess(response.code(), res,issearch,word,category)

                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: ${response}")
                    Log.d(TAG, "onResponse: ${response.body()}")
                    Log.d(TAG, "onResponse: ${response.errorBody()}")

                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<ListDTO>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
//멀티파트(참고) : https://velog.io/@dldmswo1209/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EC%97%90%EC%84%9C-%EC%84%9C%EB%B2%84%EB%A1%9C-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%A0%84%EC%86%A1%ED%95%98%EA%B8%B0
    fun insertProduct(token:String,product: RequestBody,images: MutableList<MultipartBody.Part>) {
        RetrofitUtil.ProductService.insertProduct(token,product,images).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val res = response.body()
                Log.d(TAG, "onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "onResponse insert: ${response.code()}")
                        Log.d(TAG, "onResponse: $response")
                        true
                    }
                } else {
                    Log.d(TAG, "onResponseinsert:false code = :${response.code()} ")
                    Log.d(TAG, "onResponseinsert: ${response.body()}")
                    Log.d(TAG, "onResponse: ${response}")

                    false
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onResponseinsert:false ${t.message}")

            }
        })
    }
    fun getProductDetail(productId:Long,callback:RetrofitCallback<ProductDetailDTO>){
        RetrofitUtil.ProductService.getProudctDetail(productId).enqueue(object : Callback<ProductDetailDTO> {
            override fun onResponse(call: Call<ProductDetailDTO>, response: Response<ProductDetailDTO>) {
                val res = response.body()
                Log.d(TAG, "Detail_onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        callback.onSuccess(response.code(),res,false,null,null)
                        Log.d(TAG, "Detail_onResponse : ${response.code()}")
                        true
                    }
                } else {
                    Log.d(TAG, "Detail_onResponse:false :${response.code()} ")
                    Log.d(TAG, "onResponse: ${response.code()}")

                    false
                }
            }
            override fun onFailure(call: Call<ProductDetailDTO>, t: Throwable) {
                Log.d(TAG, "onResponse:false ${t.message}")

            }
        })
    }
    fun pullProduct(token:String,productId: Long):Boolean{
        var issucess=false

        RetrofitUtil.ProductService.pullProduct(token,productId).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "Detail_onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "Detail_onResponse : ${response.code()}")
                        issucess=true
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
        return issucess
    }
    fun editProudct(token:String,productId:Long,product: RequestBody,images: MutableList<MultipartBody.Part>){
        RetrofitUtil.ProductService.editProduct(token,productId,product,images).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
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
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "onResponse:false ${t.message}")

            }
        })
    }
    fun deleteProduct(token:String,productId: Long) :Boolean{
        var issucess=false

        RetrofitUtil.ProductService.deleteProduct(token,productId).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "Detail_onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "Detail_onResponse : ${response.code()}")
                        issucess=true
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
        return issucess
    }
    fun insertFavorite(token:String,productId: Long) :Boolean{
        var issucess=false

        val categoryInterface: Call<Unit> = RetrofitUtil.ProductService.insertFavorite(token,productId)

        categoryInterface.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "onResponse res 값: $res")
                if(response.isSuccessful){
                    if (res != null) {
                        issucess=true
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, t.message ?: "오류")
            }
        })
        return issucess
    }
    fun deleteFavorite(token:String,productId: Long) :Boolean{
        var issucess=false

        val categoryInterface: Call<Unit> = RetrofitUtil.ProductService.deleteFavorite(token,productId)

        categoryInterface.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "onResponse res 값: $res")
                if(response.isSuccessful){
                    if (res != null) {
                        issucess=true
                    }
                    Log.d(TAG, "onResponse: $res")
                } else {
                    Log.d(TAG, "onResponse: Error Code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, t.message ?: "오류")
            }
        })
        return issucess
    }
    fun changeOnBoradCast(token:String,productId: Long){
        RetrofitUtil.ProductService.changeOnBoradCast(token,productId).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val res = response.body()
                Log.d(TAG, "Change_onResponse: ${res}")
                if (response.isSuccessful) {
                    if (res != null) {
                        Log.d(TAG, "Change_onResponse : ${response.code()}")
                        true
                    }
                } else {
                    Log.d(TAG, "Change_onResponse:false :${response.code()} ")
                    Log.d(TAG, "onResponse: ${response.code()}")

                    false
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, "onResponse:false ${t.message}")

            }
        })
    }
}