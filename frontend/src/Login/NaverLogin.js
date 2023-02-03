// 로그인 페이지

import React from 'react'
import axios from 'axios'
import './Login.css'

const NaverLogin = () => {

  // 카카오 로그인 함수를 실행시키면 아래에 설정해 놓은 KAKAO_AUTH_URL 주소로 이동한다.
  // 이동 된 창에서 kakao 계정 로그인을 시도할 수 있으며 로그인 버튼 클릭 시 Redirect URI로 이동하면서 빈 화면과 함게 인가코드가 발급된다.(인가코드는 파라미터 값에 들어가 있다!)
  // const REST_API_KEY = '5d6f9b9973706d6389a07d2bcf07e40b';
  // const REDIRECT_URI = 'https://localhost:3000/oauth';
  // const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const NAVER_AUTH_URL =  'https://i8d206.p.ssafy.io/oauth2/authorization/naver'

  const naverlogin = () => {
    // window.location.href = KAKAO_AUTH_URL;
    axios.get(NAVER_AUTH_URL, { withCredentials: true })
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
      <a href="https://i8d206.p.ssafy.io/oauth2/authorization/naver"><img src="naver_login.png" className='naverLogin' alt="네이버로그인" /></a>
      {/* <img src="naver_login.png"  alt="네이버로그인" onClick={naverlogin}/> */}
    </React.Fragment>
  )
}

export default NaverLogin