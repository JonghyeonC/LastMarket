// 로그인 페이지

import React from 'react'
import axios from 'axios'

const KakaoLogIn = () => {

  // 카카오 로그인 함수를 실행시키면 아래에 설정해 놓은 KAKAO_AUTH_URL 주소로 이동한다.
  // 이동 된 창에서 kakao 계정 로그인을 시도할 수 있으며 로그인 버튼 클릭 시 Redirect URI로 이동하면서 빈 화면과 함게 인가코드가 발급된다.(인가코드는 파라미터 값에 들어가 있다!)
  // const REST_API_KEY = '5d6f9b9973706d6389a07d2bcf07e40b';
  // const REDIRECT_URI = 'https://localhost:3000/oauth';
  // const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const KAKAO_AUTH_URL =  'http://treenovel.tk:8080/oauth2/authorization/kakao'

  const kakaoLogin = () => {
    // window.location.href = KAKAO_AUTH_URL;
    axios.get(KAKAO_AUTH_URL)
    .then((res) => {
      console.log(res.data)
    })
    .catch((res) => {
      console.log("실패")
      console.log(res)
    })
  }

  return (
    <React.Fragment>
      <img src="kakao_login_medium_wide.png" alt="카카오로그인" onClick={kakaoLogin}/>
    </React.Fragment>
  )
}

export default KakaoLogIn