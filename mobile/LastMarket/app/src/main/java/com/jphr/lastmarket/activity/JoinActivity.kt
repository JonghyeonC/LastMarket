package com.jphr.lastmarket.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.jphr.lastmarket.R
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.Account
import com.kakao.sdk.user.model.User
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse


private const val TAG = "JoinActivity"

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        var kakao: ImageButton = findViewById(R.id.kakao)
        var naver: NidOAuthLoginButton = findViewById(R.id.naver)


        kakao.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                kakaoLogin(this)
            } else {
                Log.d(TAG, "onCreate: kakao login  is not available-카카오톡 설치 필요")
                changeActivity()        //TODO:임시로 화면 넘어가게 하려고 넣은거라서 나중에 빼야함

            }

        }
        naver.setOnClickListener {
            naverLogin()
        }

    }

    fun kakaoLogin(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                kakaoGetUserInfo()
                changeActivity()
            }
        }
    }
    fun naverLogin(){
        var age = ""
        var email = ""
        var gender = ""
        val oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 API 호출 성공 시 유저 정보를 가져온다
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
                        age = result.profile?.age.toString()
                        email = result.profile?.email.toString()
                        gender = result.profile?.gender.toString()
                        Log.e(TAG, "네이버 로그인한 유저 정보 - 이름 : $age")
                        Log.e(TAG, "네이버 로그인한 유저 정보 - 이메일 : $email")
                        Log.e(TAG, "네이버 로그인한 유저 정보 - 성별 : $gender")
                        changeActivity()
                    }

                    override fun onError(errorCode: Int, message: String) {
                        Log.d(TAG, "onError: ")
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        Log.d(TAG, "onFailure: ")
                    }
                })
            }

            override fun onError(errorCode: Int, message: String) {
                val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                Log.e(TAG, "naverAccessToken : $naverAccessToken")
                Log.d(TAG, "onError: ")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "onFailure: ")
            }
        }

        NaverIdLoginSDK.authenticate(this, oAuthLoginCallback)
    }
    fun kakaoGetUserInfo() {
        val TAG = "getUserInfo()"
        UserApiClient.instance.me { user: User?, meError: Throwable? ->
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError)
            } else {
                println("로그인 완료")
                Log.i(TAG, user.toString())
                run {
                    Log.i(
                        TAG, """
         사용자 정보 요청 성공
         회원번호: ${user?.id}
         이메일: ${user?.kakaoAccount!!.email}
         """.trimIndent()
                    )
                }
//TODO: 받을 정보 저장하기
//                if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
//                if (user.kakaoAccount?.genderNeedsAgreement == true) { scopes.add("gender") }
//                if (user.kakaoAccount?.ageRangeNeedsAgreement == true) { scopes.add("age_range") }


                val user1: Account? = user?.kakaoAccount
                println("사용자 계정$user1")
            }
            null
        } as (User?, Throwable?) -> Unit
    }
    fun changeActivity(){
        var intent=Intent(this, UserInfoActivity::class.java)
        startActivity(intent)

    }
}
