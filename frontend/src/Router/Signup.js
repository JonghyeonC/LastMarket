import "./Signup.css"
import { FaAngleDown } from "react-icons/fa"
import { useState } from "react"
import axios from "axios"

function Signup() {
  
  let [ nickName, setNickName ] = useState('')
  let [ job, setJob ] = useState('')
  let [ interest, setInterest ] = useState('')

  console.log(nickName)
  console.log(job)
  console.log(interest)

  const sendInfor = (() => {
    return (
      axios({
        method : 'post',
        url : `http://www.123.com/user`,
        data : nickName, job, interest
      })
      .then((res) => {
        console.log(res)
      })
    )
  })
  
  return (
    <div className='Signup'>
      <h1>회원가입</h1>
      <p>당신의 라이프스타일을 알려주세요!</p>
      <div className='nameWrap'>
        <p className="labelBox">회원님의 닉네임을 알려주세요!</p>
        <input type="text" className="nickNameInput" onChange={(e) => setNickName(e.target.value)}/>
      </div>
      <br />
      <div className='nameWrap'>
        <p className="labelBox">회원님은 어떤 일을 하시나요?</p>
        <div className='signupbox'>
          <input id="dropdown1" type="checkbox" />
          <label className='dropdownLabel1' for="dropdown1">
            <div>어떤 일을 하는지 선택해주세요</div>
            <FaAngleDown className='caretIcon' />
          </label>
          <div className='content'>
            <ul>
              <li onClick={() => {setJob(1)}}>1</li>
              <li onClick={() => {setJob(2)}}>2</li>
              <li onClick={() => {setJob(3)}}>3</li>
            </ul>
          </div>
        </div>
      </div>
      <br />
      <div className='nameWrap'>
        <p className="labelBox">회원님은 어떤 것에 관심이 있나요?</p>
        <div className='signupbox'>
          <input id="dropdown2" type="checkbox" />
          <label className='dropdownLabel1' for="dropdown2">
            <div>어떤 것에 관심이 있는지 선택해주세요</div>
            <FaAngleDown className='caretIcon' />
          </label>
          <div className='content'>
            <ul>
              <li onClick={() => {setInterest('운동')}}>운동</li>
              <li onClick={() => {setInterest('요리')}}>요리</li>
              <li onClick={() => {setInterest('독서')}}>독서</li>
            </ul>
          </div>
        </div>
      </div>
      <br />
      <div className="sendBtn">
        <button onClick={sendInfor} >제출</button>
      </div>
    </div>
  )
}

export default Signup