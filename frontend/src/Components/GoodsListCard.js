import { useNavigate } from "react-router-dom"


function GoodsListCard(props) {
  let navigate = useNavigate()
  return (
    <div className='col-sm-6 col-md-4 col-lg-3' onClick={() => { navigate('/detail/' + (props.product.productId)) }}>
      <div className="GoodsCard">
        <img src={props.product.imgURI} width="200px" height="200px" alt="" />
        {/* <img src={process.env.PUBLIC_URL + '/logo192.png'} />  */}
        <h4>{props.product.title}</h4>
        <p>{props.product.startingPrice}</p>
      </div>
    </div>
  )
}

export default GoodsListCard