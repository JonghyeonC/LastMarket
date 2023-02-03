import products from "../Data"
import { useParams } from "react-router-dom"
import Discription from "../Pages/Detail/Discription"
import { useNavigate } from "react-router-dom"
import axios from "axios"

function Detail() {

  const { id } = useParams()
  const navigate = useNavigate()
  
  function liveroom() {
    const url = `https://i8d206.p.ssafy.io/api/liveroom/${id}`

    axios.get(url)
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
      <Discription id={id} />
      <button onClick={liveroom}>라이브 시청</button>
      <button>구매</button>
    </div>
  )
}

export default Detail