import './InputBox.css'
import Dropbox from '../../Components/Dropbox'
import { useState } from 'react'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';


function InputPage() {
  
  const [startDate, setStartDate] = useState(new Date());

  const [checkbox, setCheckbox] = useState(false)
  
  const [name, setName] = useState('')
  const [content, setContent] = useState('')
  const [price, setPrice] = useState('')
  const [bid, setBid] = useState('')
  const [cate, SetCate] = useState('')

  console.log(name)
  console.log(content)
  console.log(price)
  console.log(bid)
  console.log(cate)
  console.log(startDate)
  
  return (
    <div className='FormInput'>
      <input type="text" className='BoxInput' placeholder=' 상품명 입력' onChange={(e) => setName(e.target.value)}/>
      <br />
      <textarea name="" id="" cols="30" rows="10" placeholder='상품과의 이야기를 알려주세요' height="180px" onChange={(e) => setContent(e.target.value)}></textarea>
      <br />
      <Dropbox SetCate={SetCate} />
      <br />
      <input type="text" className='BoxInput' placeholder=' 즉시 판매가 입력' onChange={(e) => setPrice(e.target.value)}/>
      <br />
      <div className='checkbox'>
        <input type="checkbox" className='check' onClick={() => {setCheckbox(!checkbox)}}/>
        {
          checkbox ?
          <div>
            <div>
              <input type="text" className='bidInput' placeholder=' 경매 시작가 입력' onChange={(e) => setBid(e.target.value)}/> 
            </div>
            <br />
            <DatePicker
              className='TimeInput'
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              showTimeSelect
              timeFormat="HH:mm"
              timeIntervals={15}
              timeCaption="time"
              dateFormat="MMMM d, yyyy h:mm aa"
            />
          </div> :
          <div className='bidBox'><p className='liveText'>라이브 방송을 하시려면 체크해주세요</p></div>
        }
      </div>
    </div>
  )
}

export default InputPage