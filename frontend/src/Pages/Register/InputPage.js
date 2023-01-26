import './InputBox.css'
import Dropbox from '../../Components/Dropbox'
import { useState } from 'react'

function InputPage() {

  const [checkbox, setCheckbox] = useState(false)
  
  const [name, setName] = useState('')
  const [price, setPrice] = useState('')
  const [bid, setBid] = useState('')

  console.log(name)
  console.log(price)
  console.log(bid)

  return (
    <div className='InputForm'>
      <input type="text" className='InputBox' placeholder=' 상품명 입력' onChange={(e) => setName(e.target.value)}/>
      <br />
      <Dropbox />
      <br />
      <input type="text" className='InputBox' placeholder=' 즉시 판매가 입력' onChange={(e) => setPrice(e.target.value)}/>
      <br />
      <div className='checkbox'>
        <input type="checkbox" className='check' onClick={() => {setCheckbox(!checkbox)}}/>
        {
          checkbox ?
          <input type="text" className='bidInput' placeholder=' 경매 시작가 입력' onChange={(e) => setBid(e.target.value)}/> :
          <div className='textBox'><p className='liveText'>라이브 방송을 하시려면 체크해주세요</p></div>
        }
      </div>
    </div>
  )
}

export default InputPage