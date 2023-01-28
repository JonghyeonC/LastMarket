import { useNavigate } from "react-router-dom"

function Card(props) {
  let navigate = useNavigate()
  return (
    <div className='col-md-3' onClick={() => { navigate('/detail/' + (props.i)) }}>
      <img src={"https://codingapple1.github.io/shop/shoes" + (props.i + 1)+ ".jpg"} width="100px" height="100px" alt="" />
      {/* <img src={process.env.PUBLIC_URL + '/logo192.png'} />  */}
      <h4>{props.product.title}</h4>
      <p>{props.product.price}</p>
    </div>
  )
}

export default Card