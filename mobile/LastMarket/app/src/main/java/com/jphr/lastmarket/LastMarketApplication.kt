package com.jphr.lastmarket

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastMarketApplication:Application() {
    companion object{
        //전역변수를 통해 앱실행시 1번만 생성(singleton)
        lateinit var wRetrofit: Retrofit
        var baseurl=""//TODO: BASEURL나오면 입력해주기
    }
    override fun onCreate() {
        super.onCreate()

        wRetrofit=Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_key))
    }
}