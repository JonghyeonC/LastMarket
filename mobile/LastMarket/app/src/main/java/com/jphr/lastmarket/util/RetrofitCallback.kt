package com.jphr.lastmarket.util

interface RetrofitCallback<T> {
    fun onError(t: Throwable)

    fun onSuccess(code: Int, responseData: T, option:Boolean)

    fun onFailure(code: Int)
}