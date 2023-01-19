import "./Signup.css"
import { FaAngleDown } from "react-icons/fa"

function Signup() {

  return (
    <div className='Signup'>
      <h1>회원가입</h1>
      <p>당신의 라이프스타일을 알려주세요!</p>
      <div className='nameWrap'>
        <p>회원님의 닉네임을 알려주세요!</p>
        <input type="text" />
      </div>
      <div className='nameWrap'>
        <p>회원님은 어떤 일을 하시나요?</p>
        <div className='container'>
          <input id="dropdown1" type="checkbox" />
          <label className='dropdownLabel1' for="dropdown1">
            <div>어떤 일을 하는지 선택해주세요</div>
            <FaAngleDown className='caretIcon' />
          </label>
          <div className='content'>
            <ul>
              <li>1</li>
              <li>1</li>
              <li>1</li>
            </ul>
          </div>
        </div>
      </div>
      <div className='nameWrap'>
        <p>회원님은 어떤 것에 관심이 있나요?</p>
        <div className='container'>
          <input id="dropdown2" type="checkbox" />
          <label className='dropdownLabel1' for="dropdown2">
            <div>어떤 것에 관심이 있는지 선택해주세요</div>
            <FaAngleDown className='caretIcon' />
          </label>
          <div className='content'>
            <ul>
              <li>1</li>
              <li>1</li>
              <li>1</li>
            </ul>
          </div>
        </div>
      </div>

    </div>
  )
}

export default Signup