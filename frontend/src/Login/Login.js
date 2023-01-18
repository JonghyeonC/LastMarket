import NaverLogin from "./NaverLogin"
import KakaoLogin from "./KakaoLogin"
import React from "react";

const Login = () => {
	

	return (
		<div>
			<p>회원가입 창</p>
			<KakaoLogin />
			<NaverLogin />
		</div>
	)
}

export default Login