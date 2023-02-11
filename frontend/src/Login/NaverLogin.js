// 로그인 페이지

import React from 'react'
import axios from 'axios'
import './Login.css'

const NaverLogin = () => {

  return (
    <React.Fragment>
      <a href="https://i8d206.p.ssafy.io/oauth2/authorization/naver"><img src="naver_login.png" className='naverLogin' alt="네이버로그인" /></a>
    </React.Fragment>
  )
}

export default NaverLogin