// import ProfileInfo from "../Components/ProfileInfo";
import ProfileGoodComponent from "../Components/ProfileGoodComponent";
import LiveChat from "../Chat";

import { useEffect, useState } from "react"
import axios from "axios"


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
        <div>
          <img src="profile_icon.png" width="230px" height="230px" alt="" />
          <br />
          <button>프로필 사진 수정</button>
        </div>
        <div>
          <h3>{nickName}</h3>
          <h3>{addrs}</h3>
          <h3>{lifestyles}</h3>
        </div>
        <div>
          <button>회원정보 수정</button>
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