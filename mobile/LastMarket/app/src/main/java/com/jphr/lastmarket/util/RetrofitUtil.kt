package com.jphr.lastmarket.util

import com.jphr.lastmarket.LastMarketApplication
import com.jphr.lastmarket.api.ChatAPI
import com.jphr.lastmarket.api.MyPageAPI
import com.jphr.lastmarket.api.ProductAPI
import com.jphr.lastmarket.api.UserInfoAPI


class RetrofitUtil {
    companion object{
        val userInfoService = LastMarketApplication.wRetrofit.create(UserInfoAPI::class.java)
        val productService = LastMarketApplication.wRetrofit.create(ProductAPI::class.java)
        val chatService = LastMarketApplication.wRetrofit.create(ChatAPI::class.java)
        val myPageService = LastMarketApplication.wRetrofit.create(MyPageAPI::class.java)
    }
}