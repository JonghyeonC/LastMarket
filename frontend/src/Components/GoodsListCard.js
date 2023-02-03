import { useNavigate } from "react-router-dom"

function GoodsListCard(props) {
  let navigate = useNavigate()
  return (
    <div className='col-md-3' onClick={() => { navigate('/detail/' + (props.product.productId)) }}>
      <img src={props.product.imgURI} width="100px" height="100px" alt="" />
      {/* <img src={process.env.PUBLIC_URL + '/logo192.png'} />  */}
      <h4>{props.product.title}</h4>
      <p>{props.product.startingPrice}</p>
    </div>
  )
}

export default GoodsListCard