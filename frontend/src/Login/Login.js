import NaverLogin from "./NaverLogin"
import KakaoLogin from "./KakaoLogin";
import React from "react";

const Login = () => {

	return (
		<div>
			<h1>LOG IN</h1>
			<KakaoLogin />
			<NaverLogin />
		</div>
	)
}

export default Login