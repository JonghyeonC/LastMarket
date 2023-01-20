package com.jphr.lastmarket.util

import android.app.Application
import com.jphr.lastmarket.LastMarketApplication
import com.jphr.lastmarket.api.UserInfoAPI


class RetrofitUtil {
    companion object{
        val UserInfoService = LastMarketApplication.wRetrofit.create(UserInfoAPI::class.java)
//        val orderService = LastMarketApplication.wRetrofit.create(OrderApi::class.java)
//        val productService = LastMarketApplication.wRetrofit.create(ProductApi::class.java)
//        val userService = LastMarketApplication.wRetrofit.create(UserApi::class.java)
    }
}