import './Dropboxcss.css'
import { FaAngleDown } from "react-icons/fa"


function Dropbox({SetCate}) {
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
            <li onClick={() => {SetCate(1)}}>1</li>
            <li onClick={() => {SetCate(2)}}>2</li>
            <li onClick={() => {SetCate(3)}}>3</li>
          </ul>
        </div>
      </div>
    </div>
  )
}

export default Dropbox