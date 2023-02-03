import UploadPage from '../Pages/Register/UploadPage'
import InputPage from '../Pages/Register/InputPage'
import { useState } from 'react'
import './Routercss.css'
import axios from 'axios'

function Register() {

  const [inputData, setInputData] = useState([])
  const [imageUrls, setImageUrls] = useState([])
  const URL = `https://i8d206.p.ssafy.io/api/product`

  let formData = new FormData()
  formData = inputData
  // console.log(inputData)
  // console.log(imageUrls)


  const reg = () => {
    
    formData.append('imgURIs', imageUrls)
    console.log(formData)
    axios({
      method: "post",
      url: URL,
      data: formData,
      headers: { "Content-Type" : "multipart/form-data", Authorization: "eyJyZWdEYXRlIjoxNjc1MjM2MjU5OTczLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInByb2ZpbGUiOiIiLCJsb2NhbHRpb24iOiIiLCJpZCI6NzAwMywidXNlcm5hbWUiOiJuYXZlcl9rd0FqYXMtU0JqMlhlaHdZMG1LVnViWFNxbjNISGZ0WHdoZG5NcGdJRERjIiwiZXhwIjoxNjc1MjM4MDU5fQ.j0Q2aPosXqsX9PmyKSKdVtr9-4eUcq895TDgk6Lyq7E" }
    })
    .then((res) => {
      console.log(1)
      console.log(res)
    })
    .catch((res) => {
      console.log("실패")
    })
  }

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
    </div>
  )
}

export default Register