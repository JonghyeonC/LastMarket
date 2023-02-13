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
        setNickName(res.data.nickname)
        setLifestyles(res.data.lifestyles)
        setAddrs(res.data.addr)
      })
      .catch((res) => console.log('여기 실패'))
    )
  })

  useEffect(() => {
    getInfo()
  }, [])

  return (
    <div>
      <div className="Profile_title">
        <h1>{nickName}님의 프로필입니다.</h1>
      </div>
      <div className="profile_Profile_Info_Com">
        <div className="profile_Profile_Info_Com_Img">
          <img src="profile_profile.png" width="250px" height="250px" alt="" />
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
            <p>{lifestyles} 라이프스타일</p>
          </div>
          <div>
            <p><img src="addr.png" width="50px" height="50px" alt="addr" /> {addrs}</p>
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