import { useEffect, useState } from "react"
import axios from "axios"

function DeleteGood(props){

  const [productId, setProductId] = useState('')

  function DeleteGoodApi(){
    return(
      axios({
        method: 'post',
        url: `https://i8d206.p.ssafy.io/api/product/${productId}`
      })
      .then((res) => {
        console.log('삭제 post 성공')
      })
      .catch((res) => console.log('삭제 post 실패'))
    )
  }

  useEffect(() => {
    DeleteGoodApi()
  }, [props])
}

export default DeleteGood