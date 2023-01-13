package com.jphr.lastmarket

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.Account
import com.kakao.sdk.user.model.User


private const val TAG = "JoinActivity"
class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        var kakao:ImageButton=findViewById(R.id.kakao)

        kakao.setOnClickListener{
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                kakaologin()
            }else Log.d(TAG, "onCreate: kakao login  is not available-카카오톡 설치 필요" )

        }



    //TODO: 네이버 로그인 구현


    }
    fun kakaologin(){
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            }
            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                getUserInfo()
            }
        }
    }
    fun getUserInfo() {
        val TAG = "getUserInfo()"
        UserApiClient.instance.me { user: User, meError: Throwable? ->
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError)
            } else {
                println("로그인 완료")
                Log.i(TAG, user.toString())
                run {
                    Log.i(
                        TAG, """
         사용자 정보 요청 성공
         회원번호: ${user.id}
         이메일: ${user.kakaoAccount!!.email}
         """.trimIndent()
                    )
                }
//TODO: 받을 정보 저장하기
//                if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
//                if (user.kakaoAccount?.genderNeedsAgreement == true) { scopes.add("gender") }
//                if (user.kakaoAccount?.ageRangeNeedsAgreement == true) { scopes.add("age_range") }



                val user1: Account? = user.kakaoAccount
                println("사용자 계정$user1")
            }
            null
        } as (User?, Throwable?) -> Unit
    }
}