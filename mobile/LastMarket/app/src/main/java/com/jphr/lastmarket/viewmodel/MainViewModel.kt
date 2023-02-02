package com.jphr.lastmarket.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDetailDTO

private const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {

    private var productId:Long=0
    private var productDetail:ProductDetailDTO?=null
    private var productDetailNew:Product?=null

    fun setProductId(id:Long){
        productId=id
    }
    fun getProductId(): Long {
        return productId
    }
    fun setProductDetail(product:ProductDetailDTO){
        productDetail=product
    }
    fun getProductDetail(): ProductDetailDTO? {
        return productDetail
    }
//    fun setProductDetailNew(product:Product){
//        productDetailNew=product
//    }
//    fun getProductDetailNew(): Product? {
//        return productDetailNew
//    }
}