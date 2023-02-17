import products from "../Data"
import { useParams } from "react-router-dom"
import Discription from "../Pages/Detail/Discription"
import { useNavigate } from "react-router-dom"
import axios from "axios"

function Detail() {

  const { id } = useParams()

  return (
    <div>
      <Discription id={id} />
    </div>
  )
}

export default Detail