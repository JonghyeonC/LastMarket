package com.jphr.lastmarket.util

interface RetrofitCallback<T> {
    fun onError(t: Throwable)

    fun onSuccess(code: Int, responseData: T, option:Boolean,word:String?,category:String?)

    fun onFailure(code: Int)
}