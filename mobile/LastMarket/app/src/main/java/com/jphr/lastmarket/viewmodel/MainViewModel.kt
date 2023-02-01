package com.jphr.lastmarket.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDTO

private const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {

    private var productDetailCategory:Product?=null
    private var productDetailLive:Product?=null
    private var productDetailNew:Product?=null

    fun setProductDetailCategory(product:Product){
        productDetailCategory=product
        Log.d(TAG, "setProductDetailCategory: 데이터 할당 $productDetailCategory")
    }
    fun getProductDetailCategory(): Product? {
        Log.d(TAG, "getProductDetailCategory: $productDetailCategory")

        return productDetailCategory
    }
//    fun setProductDetailLive(product:Product){
//        productDetailLive=product
//    }
//    fun getProductDetailLive(): Product? {
//        return productDetailLive
//    }
//    fun setProductDetailNew(product:Product){
//        productDetailNew=product
//    }
//    fun getProductDetailNew(): Product? {
//        return productDetailNew
//    }
}