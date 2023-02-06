package com.jphr.lastmarket.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.jphr.lastmarket.R
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.concurrent.thread


private const val TAG = "WebViewActivity"

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        //TODO: 헤더에 KEY-VALUE쌍으로 되어있는 토큰 LOCAL STORAGE에 저장하기

        var intent = intent
        var naverUrl = intent.getStringExtra("naver")
        var kakaoUrl = intent.getStringExtra("kakao")
        Log.d(TAG, "onCreate: ${naverUrl}")
        var webview = findViewById<WebView>(R.id.webView)

        webview.webViewClient = object : WebViewClient() {
            // From api level 24
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                // Get the tel: url
                var url = request.url.toString()
                Log.d(TAG, "shouldOverrideUrlLoading: $url")
                if (url.startsWith("https://i8d206.p.ssafy.io/api")) {
                    var i = 0
                    var len = url.length
                    var startIdx = 0
                    for (i in i..len) {
                        if (url.get(i) == '=') {
                            startIdx = i + 1
                            break
                        }
                    }
                    var token = url.substring(startIdx, len)
                    var prefs = getSharedPreferences("user_info", MODE_PRIVATE)
                    var editor = prefs?.edit()
                    editor?.putString("token", token)
                    editor?.commit()
                    Log.d(TAG, "shouldOverrideUrlLoading: $token")
                    var intent = Intent(this@WebViewActivity, UserInfoActivity::class.java)
                    startActivity(intent)

//                    val thread = thread {
//                        var okHttpClient = OkHttpClient.Builder().build()
//                        var okRequest: Request = Request.Builder()
//                            .url(request?.url.toString())
//                            .build()
//                        try {
//                            var okResponse: Response = okHttpClient.newCall(okRequest).execute()
//                            if (okResponse != null) {
//
//                                Log.d(TAG, "바부: ${okResponse}")
//                                Log.d(TAG, "headers: ${okResponse.headers}")
//                                Log.d(TAG, "headers: ${okResponse.header("Server","")}")
//
//                                var authorization: String? = okResponse.header("Authentication", "")
//                                if(authorization.isNullOrEmpty()) {
//                                    Log.d(TAG, "shouldInterceptRequest: authorization is empty")
//                                    var prefs=getSharedPreferences("user_info",MODE_PRIVATE)
//                                    var editor =prefs?.edit()
//                                    editor?.putString("token",authorization)
//                                } else {
//                                    var prefs=getSharedPreferences("user_info",MODE_PRIVATE)
//                                    var editor =prefs?.edit()
//                                    editor?.putString("token",authorization)
//                                }
//                                var intent= Intent(this@WebViewActivity,UserInfoActivity::class.java)
//                                startActivity(intent)
//                                Log.d(TAG, "shouldInterceptRequest: intnet 변경")
//                            }
//                            else {
//                                Log.d(TAG, "okResponse is null")
//                            }
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                            Log.d(TAG, "shouldInterceptRequest: ${e.printStackTrace()}")
//                        }
//                    }
//                    thread.join()
//                    Log.d(TAG, "shouldOverrideUrlLoading: starwith")

                    return false
                }
                return false
            }
//            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
//
//                val url = request?.url.toString()
//                if(url.startsWith(" https://i8d206.p.ssafy.io/api")){
//                    Log.d(TAG, "shouldInterceptRequest22: ")
//                    val thread = thread {
//                        var okHttpClient = OkHttpClient.Builder().build()
//                        var okRequest: Request = Request.Builder()
//                            .url(request?.url.toString())
//                            .build()
//                        try {
//                            var okResponse: Response = okHttpClient.newCall(okRequest).execute()
//                            if (okResponse != null) {
//
//                                Log.d(TAG, "바부2: $okResponse")
//                                Log.d(TAG, "headers2: ${okResponse.headers}")
//                                Log.d(TAG, "header auth: ${okResponse.header("Server","")}")
//
//                                var authorization: String? = okResponse.header("Authentication", "")
//                                if(authorization.isNullOrEmpty()) {
//                                    Log.d(TAG, "shouldInterceptRequest2: authorization is empty")
//                                    var prefs=getSharedPreferences("user_info",MODE_PRIVATE)
//                                    var editor =prefs?.edit()
//                                    editor?.putString("token",authorization)
//                                } else {
//                                    var prefs=getSharedPreferences("user_info",MODE_PRIVATE)
//                                    var editor =prefs?.edit()
//                                    editor?.putString("token",authorization)
//                                }
//                                var intent= Intent(this@WebViewActivity,UserInfoActivity::class.java)
//                                startActivity(intent)
//                                Log.d(TAG, "shouldInterceptRequest2: intnet 변경")
//                            }
//                            else {
//                                Log.d(TAG, "okResponse is nul2l")
//                            }
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                            Log.d(TAG, "shouldInterceptRequest2: ${e.printStackTrace()}")
//                        }
//                    }
//                    thread.join()
//                }
//                else{
//                    Log.d(TAG, "start with로 시작하지 않음2  ")
//                }
//                return super.shouldInterceptRequest(view, request)
//            }
        }
        webview.settings.loadWithOverviewMode = true
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true

        if (naverUrl != null) {
            webview.loadUrl(naverUrl)
        } else if (kakaoUrl != null) {
            webview.loadUrl(kakaoUrl)
        }

    }


}
