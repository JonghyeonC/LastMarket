import { useEffect, useState } from "react"
import axios from "axios"


function ProfileInfo() {

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
      <div>
      <img src="profile_icon.png" width="150px" height="150px" alt="" />
      <br />
      <br />
      <span>
        <p>닉네임 : {nickName}</p>
        <p>지역 : {addrs}</p>
        <p>라이프스타일 : {lifestyles}</p>
        
      </span>
      </div>
      <br />
      <div>
      <button>닉네임 수정</button>
      <br />
      <button>프로필 수정</button>
      </div>
    </div>
  )

}

export default ProfileInfo
