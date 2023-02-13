import TabContent from "./Tabcontent"
import { useEffect, useState } from "react"
import { Nav } from "react-bootstrap"
// import axios from "axios"

function ProfileGoodComponent() {

  let [tab, setTab] = useState(0)
  
  return (
    <div>
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

export default ProfileGoodComponent