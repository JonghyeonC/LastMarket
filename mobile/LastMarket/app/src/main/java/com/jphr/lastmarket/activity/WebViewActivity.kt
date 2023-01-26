package com.jphr.lastmarket.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jphr.lastmarket.R


private const val TAG = "WebViewActivity"
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        //TODO: 헤더에 KEY-VALUE쌍으로 되어있는 토큰 LOCAL STORAGE에 저장하기

        var intent=intent
        var naverUrl=intent.getStringExtra("naver")
        var kakaoUrl=intent.getStringExtra("kakao")
        Log.d(TAG, "onCreate: ${naverUrl}")
        var webview=findViewById<WebView>(R.id.webView)

        webview.webViewClient = object : WebViewClient() {
            // From api level 24
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                // Get the tel: url
                val url = request.url.toString()
                if (url.startsWith("http://treenovel.tk:8080")) {           //TODO: 나중에 도메인으로 바꾸기
                    var intent= Intent(this@WebViewActivity,UserInfoActivity::class.java)
                    startActivity(intent)
                    return false
                }
                return false
            }
        }
        webview.settings.loadWithOverviewMode=true
        webview.settings.javaScriptEnabled=true
        webview.settings.javaScriptCanOpenWindowsAutomatically=true

        if (naverUrl != null) {
            webview.loadUrl(naverUrl)
        }
        else if(kakaoUrl!=null){
            webview.loadUrl(kakaoUrl)
        }
    }
}