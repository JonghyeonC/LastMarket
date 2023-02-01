import UploadPage from '../Pages/Register/UploadPage'
import InputPage from '../Pages/Register/InputPage'
import { useState } from 'react'
import './Routercss.css'

function Register() {

  const [inputData, setInputData] = useState('')
  const [imageUrls, setImageUrls] = useState('')

  console.log(inputData)
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
      <button>등록하기</button>
    </div>
  )
}

export default Register