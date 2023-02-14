import { useNavigate } from 'react-router-dom'
import { useState } from 'react'

import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Category from "./Category";
import ModalBasic from './Login/ModalBasic';
import { useSelector } from 'react-redux';
import { addToken } from './redux/store'
import { useDispatch } from 'react-redux';

const options = [
  {
    name: 'Enable backdrop (default)',
    scroll: false,
    backdrop: true,
  },
];

function OffCanvasExample({ name, ...props }) {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const toggleShow = () => setShow((s) => !s);

  return (
    <>
    <div>
      {/* <Button variant="primary" onClick={toggleShow} className="me-2" > */}
      <img className="category_btn1" src="category_btn.png" alt="category_btn1" onClick={toggleShow} />
      {/* </Button> */}
      <Offcanvas className="cate_zone" show={show} onHide={handleClose} {...props}>
        {/* <Offcanvas.Header closeButton> */}
          <Offcanvas.Title><p className='cate_title'>지금 뭐하고 계신가요??</p></Offcanvas.Title>
        {/* </Offcanvas.Header> */}
        <hr />
        <Category />
      </Offcanvas>
    </div>
    </> 
  );
}

function Example() {
  return (
    <>
      {options.map((props, idx) => (
        <OffCanvasExample key={idx} {...props} />
      ))}
    </>
  );
}



function Navbar() {

  const dispatch = useDispatch()

  function delCookie() {
    dispatch(addToken(null))
  }

  let navigate = useNavigate()
  let [inputValue, setInputValue] = useState('')
  let reduxData = useSelector((state) => {return state})
  console.log(inputValue)

  const [modalOpen, setModalOpen] = useState(false);

  // 모달창 노출
  const showModal = () => {
      setModalOpen(true);
  };

  
  return (
    <div className='Nav' >
      {/* <div className="navbar_up">
        <div onClick={() => navigate('/live')}>
            <img className="App-logo" src="logos/App_logo.png" alt="App_logo"/>
            <span>앱 다운로드</span>
        </div>
        <div onClick={() => navigate('/register')}>고객센터</div>
      </div> */}
      {/* <hr className="nav_line1"/> */}
      <br />
      <div className='nav_last'>
        <div className='W_logo_box'>
          <img className="W_logo" src="logos/W_logo5.png" alt="W_logo" onClick={() => navigate('/')}/>
        </div>
        <div className="navbar_down">
          <div className='input_box_box'>
            <input className="nav_input" type="text" placeholder="검색해보세요" defaultValue="" onChange={(e) => setInputValue(e.target.value)} onKeyPress={(e) => { if (e.key === 'Enter') { navigate("/search/" + inputValue) }}} />
          </div>
          <span>
            { reduxData.token ? 
            <span>
              <div className="nav_btn_box"> 
                {/* <img className="chat_icon" src="chat_icon.png" alt="chat_icon" onClick={() => navigate('/chat')}/> */}
                <img className="myprofile_icon" src="myprofile_icon.png" alt="myprofile_icon" onClick={() => navigate('/profile')}/>
                <span>
                  <img className="logout_icon" src="logout_icon.png" alt="logout_icon" onclick={delCookie}/>
                  {/* {modalOpen && <ModalBasic setModalOpen={setModalOpen} />} */}
                </span>
              </div>
            </span>
            :
            <span>
              <div className="nav_btn_box">
                <img className="login_icon" src="login_icon.png" alt="login_icon" onClick={showModal} />
                <img className="signup_icon" src="signup_icon.png" alt="signup_icon" onClick={showModal} />
                {modalOpen && <ModalBasic setModalOpen={setModalOpen} />}
              </div>
            </span>
            }
            {/* <br /> */}
            {/* <br />
            <span>
              <div class="nav_btn_box">
                <a href="https://www.kakaocorp.com/"><img className="login_icon" src="login_icon.png" alt="login_icon" /></a>
                <a href="https://www.coupang.com/"><img className="signup_icon" src="signup_icon.png" alt="signup_icon" /></a>
              </div>
            </span> */}
          </span>
        </div>
      </div>
      <div>
        <span>
          <Example />
      
          {/* // onClick={() => this.setState({ isCategory: !this.state.isCategory })} */}
          {/* // />
          // <Left primary={this.state.isCategory}>
          //   <Cate>
          //   <Category />
          //   </Cate>
        // </Left> */}
          {/* <button className='sellBtn'>판매하기</button> */}
          <hr className="nav_line2"/>
        </span>
        <div>
        { 
        reduxData.token ?
        <div>
          <img className='sell_icon' src="sell_icon.png" alt="sell_icon" onClick={() => navigate('/register')} />
        </div>
        :
        null
        }
        </div>

      </div>
      <br />
    </div>
  )
}

export default Navbar