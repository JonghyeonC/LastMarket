import products from "../Data"
import { useParams } from "react-router-dom"
import Discription from "../Pages/Detail/Discription"
import { useNavigate } from "react-router-dom"

function Detail() {

  const { id } = useParams()
  const navigate = useNavigate()
  
  return (
    <div>
      <Discription id={id} />
      <button onClick={() => {navigate('/live')}}>라이브 시청</button>
      <button>구매</button>
    </div>
  )
}

export default Detail