import './Drop_catecss.css'
import { FaAngleDown } from "react-icons/fa"
import { useState } from 'react'


function Dropbox_cate({SetCate}) {

  const [value, SetValue] = useState('')

  return (
    <div className='inputWrap'>
      <div className='signupbox'>
        <input id="dropdown" type="checkbox" />
        <label className='dropdownLabel' for="dropdown">
          {
            value ?
            <div>{value}</div> :
            <div>카테고리 선택</div>
          }
          <FaAngleDown className='caretIcon' />
        </label>
        <div className='content'>
          <ul>
            {/* {arr.function.map((el, idx) => {
              <li onClick={el}>{arr.label[idx]}</li>
            })} */}
            <li onClick={() => {SetCate(1), SetValue(1)}}>1</li>
            <li onClick={() => {SetCate(2), SetValue(2)}}>2</li>
            <li onClick={(event) => {event.stopPropagation(); SetCate(3), SetValue(3)}}>3</li>
          </ul>
        </div>
      </div>
    </div>
  )
}
// arr = {
//   function: [() => {SetCate(1), SetValue(1)}, () => {SetCate(2), SetValue(2)}, (event) => {event.stopPropagation(); SetCate(3), SetValue(3)}],
//   label: ['1', '2', '3'],
// }

export default Dropbox_cate
