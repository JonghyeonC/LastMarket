import './Dropboxcss.css'
import { FaAngleDown } from "react-icons/fa"


function Dropbox() {
  return (
    <div className='inputWrap'>
    {/* <p className="labelBox">회원님은 어떤 일을 하시나요?</p> */}
      <div className='signupbox'>
        <input id="dropdown" type="checkbox" />
        <label className='dropdownLabel' for="dropdown">
          <div>카테고리 선택</div>
          <FaAngleDown className='caretIcon' />
        </label>
        <div className='content'>
          <ul>
            {/* <li onClick={() => {setJob(1)}}>1</li>
            <li onClick={() => {setJob(2)}}>2</li>
            <li onClick={() => {setJob(3)}}>3</li> */}
          </ul>
        </div>
      </div>
    </div>
  )
}

export default Dropbox