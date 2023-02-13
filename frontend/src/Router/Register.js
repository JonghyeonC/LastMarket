import './Routercss.css'
import React, { useState } from "react"
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import UploadPage from '../Pages/Register/UploadPage'
import InputPage from '../Pages/Register/InputPage'

function Register() {

  const navigate = useNavigate()
  let [inputValue, setInputValue] = useState('')

  // function gomain() {
  //   navigate('/');
  //   location.reload();
  // }

  const [inputData, setInputData] = useState([])
  const [imageUrls, setImageUrls] = useState([])
  const URL = `https://i8d206.p.ssafy.io/api/product`

  // console.log(inputData)
  console.log(imageUrls)
  
  // const test = (() => {
  //   let formData = new FormData();
  //   let jsonData = new FormData()

  //   imageUrls.map((url) => {
  //     formData.append('imgs', url)
  //   })

  //   console.log(inputData)

    
  //   for (let key of formData.keys()) {
  //     console.log(key);
  //   }
    
  //   // FormData의 value 확인
  //   for (let value of formData.values()) {
  //     console.log(value);
  //   }
  // })

  function reg(){
    
    let formData = new FormData();
    
    imageUrls.map((url) => {
      formData.append('imgs', url)
    })

    formData.append('product', new Blob([JSON.stringify(inputData)]), {type: "application/json"})

    
    for (let key of formData.keys()) {
      console.log(key);
    }
    
    // FormData의 value 확인
    for (let value of formData.values()) {
      console.log(value);
    }


    axios({
      method: "post",
      url: URL,
      data: formData,
      headers: { "Content-Type" : "multipart/form-data", Authorization: "eyJyZWdEYXRlIjoxNjc1OTE2MTQ5MDM5LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInByb2ZpbGUiOiIiLCJsb2NhbHRpb24iOiIiLCJuaWNrbmFtZSI6Iu2MkOunpOq4gCDsnpHshLEg7YWM7Iqk7Yq4IOqzhOyglSIsImlkIjoxNTA3MiwidXNlcm5hbWUiOiJrYWthb18yNjI4MDMwNDY1IiwiZXhwIjoxNjc1OTE3OTQ5fQ.CH95W-Q-k9ZtR5sWeTu2qSNSmX-9axyEUW1_DQkztu4"}
    })
    .then((res) => {
      console.log("axios 성공")
      console.log(res)
      console.log({formData})
    })
    .catch((res) => {
      console.log("axios 실패")
      console.log(res)
      console.log({formData})
    })
  }

  // function RegAndMain(){
  //   return(

  //     reg()
  //   )
  // }
  return (
    <div>
      <div className='registerBox'>
        <div className='uploadBox'>
          <UploadPage setImageUrls={setImageUrls} />
        </div>
        <div className='inputRegisterBox'>
          <InputPage setInputData={setInputData} />
        </div>
      </div>
      <button onClick={() => {reg(); navigate('/')}}>등록하기</button>
      {/* <button onClick={test}>테스트</button> */}
    </div>
  )
}

export default Register