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
                if (url.startsWith("https://i8d206.p.ssafy.io/signup")) {
                    var i = 0
                    var len = url.length
                    var startIdx = 0
                    for (i in i..len) {
                        if (url.get(i) == '=') {

                            startIdx = i + 1
                            break
                        }
                    }
                    var token = url.substring(39, len)
                    var prefs = getSharedPreferences("user_info", MODE_PRIVATE)
                    var editor = prefs?.edit()
                    editor?.putString("token", token)
                    editor?.commit()
                    Log.d(TAG, "shouldOverrideUrlLoading: $token")
                    var intent = Intent(this@WebViewActivity, UserInfoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)


                    return false
                }else if(url.startsWith("https://i8d206.p.ssafy.io/index")){
                    var len = url.length

                    var token = url.substring(38, len)
                    var prefs = getSharedPreferences("user_info", MODE_PRIVATE)
                    var editor = prefs?.edit()
                    editor?.putString("token", token)
                    editor?.commit()
                    Log.d(TAG, "shouldOverrideUrlLoading: $token")
                    var intent = Intent(this@WebViewActivity, UserInfoActivity::class.java)
                    startActivity(intent)
                }
                return false
            }

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
