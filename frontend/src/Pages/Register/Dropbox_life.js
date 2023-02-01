import './Drop_lifecss.css'
import { FaAngleDown } from "react-icons/fa"
import { useState } from 'react'

function Dropbox_life({SetLife}) {

  const [value, SetValue] = useState('')

  return (
    <div className='lifeWrap'>
      <div className='lifeBox'>
        <input id="checkDown" type="checkbox" />
        <label className='checkDownLabel' for="checkDown">
        {
            value ?
            <div>{value}</div> :
            <div>라이프스타일 선택</div>
          }
          <FaAngleDown className='iconCaret' />
        </label>
        <div className='values'>
          <ul>
            <li onClick={() => {SetLife("요리"), SetValue('요리')}}>요리</li>
            <li onClick={() => {SetLife("운동"), SetValue('운동')}}>운동</li>
            <li onClick={() => {SetLife("청소"), SetValue('청소')}}>청소</li>
          </ul>
        </div>
      </div>
    </div>
  )
}

export default Dropbox_life