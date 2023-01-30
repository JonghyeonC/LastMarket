//package com.jphr.lastmarket.api
//
//import android.R
//import android.graphics.Bitmap
//import android.os.AsyncTask
//import com.google.gson.Gson
//import com.navercorp.nid.NaverIdLoginSDK
//
//
//private class RequestApiTask :
//    AsyncTask<Void?, Void?, Bitmap?>() {
//
//    var mOAuthLoginInstance= NaverIdLoginSDK
//    override fun onPreExecute() {
//        clearProfile()
//    }
//
//    /**
//     * background thread에서 회원프로필 조회 후 프로필 이미지 생성
//     * @param params
//     * @return
//     */
//
//    override fun doInBackground(vararg params: Void?): Bitmap? { // separate thread
//
//
//        var profileImage: Bitmap? = null
//        val url = "https://openapi.naver.com/v1/nid/me"
//        val at: String = mOAuthLoginInstance.getAccessToken()
//        val jsonString: String = mOAuthLoginInstance.requestApi(mContext, at, url)
//        val map = Gson().fromJson<Map<*, *>>(
//            jsonString,
//            HashMap::class.java
//        )
//        if (map["resultcode"] == "00") {
//            val childMap = map["response"] as Map<*, *>?
//            val profileUrl = childMap!!["profile_image"].toString()
//            sb.append(
//                """
//                    message: ${map["message"]}
//
//                    """.trimIndent()
//            )
//            sb.append(
//                """
//                    resultcode: ${map["resultcode"]}
//
//                    """.trimIndent()
//            )
//            sb.append(
//                """
//                    response.email: ${childMap["email"]}
//
//                    """.trimIndent()
//            )
//            sb.append(
//                """
//                    response.nickname: ${childMap["nickname"]}
//
//                    """.trimIndent()
//            )
//            sb.append(
//                """
//                    response.age: ${childMap["age"]}
//
//                    """.trimIndent()
//            )
//            sb.append(
//                """
//                    response.gender: ${childMap["gender"]}
//
//                    """.trimIndent()
//            )
//            sb.append(
//                """
//                    response.name: ${childMap["name"]}
//
//                    """.trimIndent()
//            )
//            sb.append(
//                """
//                    response.birthday: ${childMap["birthday"]}
//
//                    """.trimIndent()
//            )
//            profileImage = uriToBitmap(profileUrl)
//        } else {
//            makeSnackBar(findViewById(R.id.content), map["message"].toString())
//        }
//        return profileImage
//    }
//
//    /**
//     * main thread에서 프로필 및 이미지를 업데이트
//     * @param profileImage
//     */
//    override fun onPostExecute(profileImage: Bitmap?) {    // main thread
//        mUserInfo.setText(sb.toString())
//        mProfileImage.setImageBitmap(profileImage)
//    }
//
//
//}
