import NaverLogin from "./NaverLogin"
import KakaoLogin from "./KakaoLogin";
import React from "react";
import './Login.css'
import axios from "axios";

const Login = () => {

	return (
		<div className="loginDiv">
			<div className="loginBox">
				<KakaoLogin />
				<br />
				<NaverLogin />
			</div>
		</div>

	)
}

export default Login