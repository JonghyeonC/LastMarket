// import ProfileInfo from "../Components/ProfileInfo";
import ProfileGoodComponent from "../Components/ProfileGoodComponent";
import LiveChat from "../Chat";

import { useEffect, useState } from "react"
import axios from "axios"

import Button from 'react-bootstrap/Button'


function Profile() {

  let [nickName, setNickName] = useState('user')
  let [lifestyles, setLifestyles] = useState('lifestyle')
  let [addrs, setAddrs] = useState('addr')

  const getInfo = (() => {
    return (
      axios({
        method: 'get',
        url: `https://i8d206.p.ssafy.io/api/user`
      })
      .then((res) => {
        console.log(res)
        console.log('여기 성공')
        // setNickName()
        // setLifestyles()
        // setAddrs()
      })
      .catch((res) => console.log('여기 실패'))
    )
  })

  useEffect(() => {
    getInfo()
  }, [])

  return (
    <div>
      <h1>{nickName}님의 프로필입니다.</h1>
      <div className="profile_Profile_Info_Com">
        <div className="profile_Profile_Info_Com_Img">
          <img src="profile_icon.png" width="230px" height="230px" alt="" />
          <br />
          <br />
          <div>
            <Button variant="secondary">프로필 사진 수정</Button>
          </div>
        </div>
        <div className="profile_Profile_Info_Com_Text">
          <div>
            <p>{nickName}</p>
          </div>
          <div>
            <p>{lifestyles}</p>
          </div>
          <div>
            <p>{addrs}</p>
          </div>
        </div>
        <div className="profile_Profile_Info_Com_Btn">
          <div>
            <Button variant="secondary">회원정보 수정</Button>
          </div>
        </div>
      </div>
      <div className="profile_Compbox">
          <div className="profile_Profile_Good_Com">
            <ProfileGoodComponent />
          </div>
          <div className="profile_Chat_Com">
            <LiveChat />
          </div>
      </div>
    </div>
  )
}

export default Profile