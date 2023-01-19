// 리다이렉트 URI 화면

import React from "react";
import styled from "styled-components";

import { useDispatch } from "react-redux";
import { actionCreators as userAction } from "../redux/modules/user";
// import Spinner from "../elements/Spinner";

const KakaoAuthHandler = (props) => {
  const dispatch = useDispatch();

  // 발급된 인가코드를 백엔드로 넘겨주기 위해 꺼내오는 작업이 필요하다.
  // code라는 이름으로 파라미터 코드 값을 꺼내오려면 아래와 같이 선언하면 된다.
  let code = new URL(window.location.href).searchParams.get("code");

  React.useEffect(() => {
	// 꺼내온 code(인가코드)를 미들웨어를 통해 백엔드로 넘겨준다.
    dispatch(userAction.kakaoLoginAC(code));
  }, []);

  return (
    <Wrap>
      <Spinner />
    </Wrap>
  )
};

const Wrap = styled.div`
  margin-top: 50px;
  min-height: 1100px;
`

export default KakaoAuthHandler;