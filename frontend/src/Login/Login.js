import { KAKAO_AUTH_URL } from "./KakaoLoginData";
import React from "react";

const Login = () => {
	

	return (
		<div>
			<p>회원가입 창</p>
			<button href={KAKAO_AUTH_URL}>
				<img src="../images/kakao_login_medium_wide.png" alt="" />
				<span>카카오계정 로그인</span>
			</button>
		</div>
	)
}

export default Login