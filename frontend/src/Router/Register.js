import UploadPage from '../Pages/Register/UploadPage'
import InputPage from '../Pages/Register/InputPage'
import { useState } from 'react'
import './Routercss.css'
import axios from 'axios'

function Register() {

  const [inputData, setInputData] = useState([])
  const [imageUrls, setImageUrls] = useState([])
  const URL = `https://i8d206.p.ssafy.io/api/product`

  // console.log(inputData)
  console.log(imageUrls)
  
  const test = (() => {
    let formData = new FormData();
    
    console.log(inputData)

    imageUrls.imgs.map((url) => {
      formData.append('imgs', url)
    })

    formData.append(
      "category", JSON.stringify(inputData.category)
    )
    formData.append(
      "title", JSON.stringify(inputData.title)
    )
    formData.append(
      "content", JSON.stringify(inputData.content)
    )
    formData.append(
      "instantPrice", JSON.stringify(inputData.instantPrice)
    )
    formData.append(
      "lifestyle", JSON.stringify(inputData.lifestyle)
    )
    formData.append(
      "livetime", JSON.stringify(inputData.livetime)
    )
    formData.append(
      "startingPrice", JSON.stringify(inputData.startingPrice)
    )


    // imageUrls.imgs.map((url) => {
    //   formData.append('imgs', url)
    // })

    // formData.append('imgURIs', Blob, imageUrls)
    // console.log(1)
    // console.log(inputData)
    
    for (let key of formData.keys()) {
      console.log(key);
    }
    
    // FormData의 value 확인
    for (let value of formData.values()) {
      console.log(value);
    }
  })


  const reg = (() => {
    
    let formData = new FormData();

    imageUrls.imgs.map((url) => {
      formData.append('imgs', url)
    })

    formData.append(
      "category", JSON.stringify(inputData.category)
    )
    formData.append(
      "title", JSON.stringify(inputData.title)
    )
    formData.append(
      "content", JSON.stringify(inputData.content)
    )
    formData.append(
      "instantPrice", JSON.stringify(inputData.instantPrice)
    )
    formData.append(
      "lifestyle", JSON.stringify(inputData.lifestyle)
    )
    formData.append(
      "livetime", JSON.stringify(inputData.livetime)
    )
    formData.append(
      "startingPrice", JSON.stringify(inputData.startingPrice)
    )
    
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
      data: {
        formData,
      },
      headers: { "content-type" : "multipart/form-data", Authorization: "eyJyZWdEYXRlIjoxNjc1NjYzMzU5OTc5LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInByb2ZpbGUiOiIiLCJsb2NhbHRpb24iOiIiLCJuaWNrbmFtZSI6IuyVhOuLiCIsImlkIjo5MDIzLCJ1c2VybmFtZSI6Imtha2FvXzI2MjgwMzAxMjIiLCJleHAiOjE2NzU2NjUxNTl9.OU-x__6pHNV4nLJ9ZZbY_BqGtDzqQu9k0uByFWQnMRQ"}
    })
    .then((res) => {
      console.log(1)
      console.log(res)
    })
    .catch((res) => {
      console.log("실패")
      console.log(res)
    })
  })

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
      <button onClick={reg}>등록하기</button>
      <button onClick={test}>테스트</button>
    </div>
  )
}

export default Register