import TabContent from "../Components/Tabcontent"
import { useState } from "react"
import { Nav } from "react-bootstrap"

function Profile() {

  let [tab, setTab] = useState(0)
  let [nickName, setNickName] = useState('user')
  return (
    <div>
      <div>
        <img src="profile_icon.png" width="150px" height="150px" alt="" />
        <span>닉네임 : {nickName}</span>
        <span><button>닉네임 수정</button></span>
        <br />
        <button>프로필 수정</button>
      </div>
      <div>
      <Nav variant="tabs"  defaultActiveKey="link0">
        <Nav.Item>
          <Nav.Link eventKey="link0" onClick={() => { setTab(0) }}>판매중인 상품</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="link1" onClick={() => { setTab(1) }}>찜 목록</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link eventKey="link2" onClick={() => { setTab(2) }}>후기</Nav.Link>
        </Nav.Item>
      </Nav>
      </div>

      <TabContent tab={tab} />
    </div>
  )
}

export default Profile