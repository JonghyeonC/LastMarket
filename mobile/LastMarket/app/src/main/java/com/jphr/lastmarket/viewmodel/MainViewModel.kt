package com.jphr.lastmarket.viewmodel

import androidx.lifecycle.ViewModel
import com.jphr.lastmarket.dto.ChatDTO
import com.jphr.lastmarket.dto.Product
import com.jphr.lastmarket.dto.ProductDetailDTO
import com.jphr.lastmarket.dto.ProductX

private const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {

    private var productId:Long=0
    private var productDetail:ProductDetailDTO?=null
    private var productCategory:String?=null
    private var productList:MutableList<ProductX>?=null
    private var category=""
    private var word=""
    private var chatDTO:ChatDTO?=null
    var isReservation:String?=""

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
    fun setProduct(product:MutableList<ProductX>){
        productList=product
    }
    fun getProduct(): MutableList<ProductX>? {
        return productList
    }
    fun setCategory(category:String){
        this.category=category
    }
    fun getCategory(): String {
        return category
    }
    fun setWord(word:String){
        this.word=word
    }
    fun getWord(): String {
        return word
    }
    fun setChatDTO(chatDTO:ChatDTO){
       this.chatDTO=chatDTO
    }
    fun getChatDTO() :ChatDTO?{
        return chatDTO
    }
}