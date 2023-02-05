import './InputBox.css'
import Dropbox_cate from './Dropbox_cate'
import Dropbox_life from './Dropbox_life'
import { useState } from 'react'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import { useEffect } from 'react'
import moment from 'moment/moment'

function InputPage({setInputData}) {
  
  const [checkbox, setCheckbox] = useState(false)
  
  const [name, setName] = useState('')
  const [content, setContent] = useState('')
  const [price, setPrice] = useState('')
  const [bid, setBid] = useState('')
  const [cate, SetCate] = useState('')
  const [life, SetLife] = useState('')
  const [startDate, setStartDate] = useState(null);

  console.log(startDate)
  useEffect(() => {
    const serialize = {
      title: name,
      content: content,
      instantPrice: Number(price),
      startingPrice: Number(bid),
      category: cate,
      lifestyle: life,
      livetime: moment(startDate).format("YYYY-MM-DDTHH:mm:sszz")
    }
    setInputData(serialize)
  }, [name, content, price, bid, cate, life, startDate])

  return (
    <div className='FormInput'>
      <input type="text" className='BoxInput' placeholder=' 상품명 입력' onChange={(e) => setName(e.target.value)}/>
      <br />
      <textarea name="" id="" cols="30" rows="10" placeholder='상품과의 이야기를 알려주세요' height="180px" onChange={(e) => setContent(e.target.value)}></textarea>
      <br />
      <Dropbox_cate SetCate={SetCate} />
      <br />
      <input type="text" className='BoxInput' placeholder=' 즉시 판매가 입력' onChange={(e) => setPrice(e.target.value)}/>
      <br />
      <Dropbox_life SetLife={SetLife} />
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
              placeholderText='경매를 시작할 시간을 정해주세요!'
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              showTimeSelect
              timeFormat="HH:mm"
              timeIntervals={10}
              timeCaption="time"
              dateFormat="yyyy.MM.dd h:mm aa"
            />
          </div> :
          <div className='bidBox'><p className='liveText'>라이브 방송을 하시려면 체크해주세요</p></div>
        }
      </div>
    </div>
  )
}

export default InputPage