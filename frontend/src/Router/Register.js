import UploadPage from '../Pages/Register/UploadPage'
import InputPage from '../Pages/Register/InputPage'
import './Routercss.css'

function Register() {

  return (
    <div>
      <div className='registerBox'>
        <div className='uploadBox'>
          <UploadPage />
        </div>
        <div className='inputRegisterBox'>
          <InputPage />
        </div>
      </div>
      <button>등록하기</button>
    </div>
  )
}

export default Register