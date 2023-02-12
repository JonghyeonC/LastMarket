import TabContent from "../Components/Tabcontent"
import { useEffect, useState } from "react"
import { Nav } from "react-bootstrap"
import axios from "axios"

function ProfileComponent() {

  let [tab, setTab] = useState(0)
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
      <div>
      <Nav variant="tabs"  defaultActiveKey="link0">
        <Nav.Item>
          <Nav.Link eventKey="link0" onClick={() => { setTab(0) }}>판매 중인 상품</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="link1" onClick={() => { setTab(1) }}>받은 후기</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="link2" onClick={() => { setTab(2) }}>찜 목록</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="link3" onClick={() => { setTab(3) }}>구매한 상품 목록</Nav.Link>
        </Nav.Item>
      </Nav>
      </div>

      <TabContent tab={tab} />
    </div>
  )
}

export default ProfileComponent