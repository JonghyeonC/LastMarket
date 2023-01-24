import { useNavigate } from 'react-router-dom'
import { useState } from 'react'

function Navbar() {
  
  let navigate = useNavigate()
  let [inputValue, setInputValue] = useState('')
  console.log(inputValue)
  return (
    <div >
      <div className="navbar_up">
        <div onClick={() => navigate('/live')}>
            <img className="App-logo" src="logos/App_logo.png" alt="App_logo"/>
            <span>앱 다운로드</span>
        </div>
        <a href="https://www.google.com">고객센터</a>
      </div>
      <hr className="nav_line1"/>
      <div className="navbar_down">
        <img className="W_logo" src="logos/W_logo.png" alt="W_logo" onClick={() => navigate('/')}/>
        <span><input className="nav_input" type="text" placeholder="검색해보세요" onChange={(e) => setInputValue(e.target.value)} onKeyPress={(e) => { if (e.key === 'Enter') { navigate("/search/" + inputValue) } }} /></span>
        <span>
          <span>
            <div className="nav_btn_box"> 
              <img className="chat_icon" src="chat_icon.png" alt="chat_icon" onClick={() => navigate('/chat')}/>
              <img className="profile_icon" src="profile_icon.png" alt="profile_icon" onClick={() => navigate('/profile')}/>
              <img className="logout_icon" src="logout_icon.png" alt="logout_icon" onClick={() => navigate('/login')}/>
            </div>
          </span>
          {/* <br /> */}
          {/* <span>
            <div className="nav_btn_box">
              <img className="login_icon" src="login_icon.png" alt="login_icon" onClick={() => navigate('/login')} />
              <img className="signup_icon" src="signup_icon.png" alt="signup_icon" onClick={() => navigate('/signup')} />
            </div>
          </span> */}
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