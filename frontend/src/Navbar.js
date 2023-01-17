function Navbar() {
  return (
    <div >
      <div className="navbar_up">
        <a href="https://www.naver.com">
            <img className="App-logo" src="logos/App_logo.png" alt="App_logo" />
            <span>앱 다운로드</span>
        </a>
        <a href="https://www.google.com">고객센터</a>
      </div>
      <hr className="nav_line1"/>
      <div className="navbar_down">
        <a href="https://www.daum.net"><img class="W_logo" src="logos/W_logo.png" alt="W_logo"/></a>
        <span><input className="nav_input" type="text" placeholder=" 검색해보세요" /></span>
        <span>
          <span>
            <div class="nav_btn_box">
              <a href="https://www.kakaocorp.com/"><img className="chat_icon" src="chat_icon.png" alt="chat_icon" /></a>
              <a href="https://www.coupang.com/"><img className="profile_icon" src="profile_icon.png" alt="profile_icon" /></a>
              <a href="https://www.daangn.com/"><img className="logout_icon" src="logout_icon.png" alt="logout_icon" /></a>
            </div>
          </span>
          {/* <br />
          <span>
            <div class="nav_btn_box">
              <a href="https://www.kakaocorp.com/"><img className="login_icon" src="login_icon.png" alt="login_icon" /></a>
              <a href="https://www.coupang.com/"><img className="signup_icon" src="signup_icon.png" alt="signup_icon" /></a>
            </div>
          </span> */}
        </span>
      </div>
      <hr className="nav_line2"/>
    </div>
  )
}

export default Navbar