function Navbar() {
  return (
    <div >
      <div className="navbar_up">
        <span>앱 다운로드</span>
        <span>고객센터</span>
      </div>
      <hr />
      <div className="navbar_down">
        <span>LAST MARKET</span>
        <span><input className="input" type="text" placeholder="search" /></span>
        <span>
          <span>
            <div className="navbar_down">
              <span>채팅</span>
              <span>메인페이지</span>
              <span>로그아웃</span>
            </div>
        
          </span>
        </span>
      </div>
    </div>
  )
}

export default Navbar