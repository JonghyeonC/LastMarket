import NaverLogin from "./NaverLogin"
import KakaoLogin from "./KakaoLogin";
import React from "react";
import axios from "axios";

const Login = () => {

	const url = 'https://i8d206.p.ssafy.io/oauth2/authorization/naver'

	// const NaverLogin = () => {
  //   axios.get(url)
  //   .then((res) => {
  //     console.log(res.data)
  //   })
  //   .catch((res) => {
  //     console.log("실패")
  //   })
  // }

	const NaverLogin = () => {
		<a href="https://i8d206.p.ssafy.io/oauth2/authorization/naver"></a>
	}

	return (
		<div>
			<h1>LOG IN</h1>
			<KakaoLogin />
			{/* <NaverLogin /> */}
			<button onClick={NaverLogin}>네이버로그인</button>
		</div>
	)
}

export default Login