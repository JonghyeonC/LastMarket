import { useEffect, useState } from "react"
import axios from "axios"

function DeleteGood(props){

  const [ProductId, setProductId] = useState('')

  function DeleteGoodApi(){
    return(
      axios({
        method: 'post',
        url: `https://i8d206.p.ssafy.io/api/product/${props.id}`
      })
      .then((res) => {
        console.log('삭제 post 완료')
      })
      .catch((res) => {
        console.log('삭제 post 실패')
      })
    )
  }

  useEffect(() => {
    DeleteGoodApi()
  }, [props.id])

}

export default DeleteGood