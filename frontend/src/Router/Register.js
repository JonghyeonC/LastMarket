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

      headers: { "Content-Type" : "multipart/form-data", Authorization: "eyJyZWdEYXRlIjoxNjc2MDA2NzQ0MDYwLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInByb2ZpbGUiOiIiLCJsb2NhbHRpb24iOiLqsr3sg4HrtoHrj4Qg6rWs66-47IucIOyehOyImOuPmSIsIm5pY2tuYW1lIjoi7J20IOycoOyggOuKlCDrtojrn4kg7Jyg7KCA7J6F64uI64ukIiwiaWQiOjE1MTE3LCJ1c2VybmFtZSI6Imtha2FvXzI2MjgwMzA0NjUiLCJleHAiOjE2NzYwMDg1NDR9.YeCKVhbJZv81f5ZzODpRMmriTpPNY9nLG9v2Q0vvmCs"}
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