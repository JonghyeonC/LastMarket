package com.jphr.lastmarket.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.jphr.lastmarket.R
import com.jphr.lastmarket.databinding.ActivityJoinBinding
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.Account
import com.kakao.sdk.user.model.User
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import java.math.BigInteger
import java.security.SecureRandom


private const val TAG = "JoinActivity"

class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    lateinit var naverData: SharedPreferences
    var saveNaverLoginData = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var kakao: ImageButton = findViewById(R.id.kakao)
        var naver: NidOAuthLoginButton = findViewById(R.id.naver)

//        naverData=getSharedPreferences("naverData",MODE_PRIVATE)
//        load()


        kakao.setOnClickListener {
//                var token=generateState()
//                kakao_save(token)
            var str = "https://i8d206.p.ssafy.io/oauth2/authorization/kakao"
            var intent = Intent(this@JoinActivity, WebViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.putExtra("kakao", str)
            startActivity(intent)

        }

        naver.setOnClickListener {
//                var token=generateState()
//                naver_save(token)
            var str = "https://i8d206.p.ssafy.io/oauth2/authorization/naver"
            var intent = Intent(this@JoinActivity, WebViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

            intent.putExtra("naver", str)
            startActivity(intent)
        }
    }

}


