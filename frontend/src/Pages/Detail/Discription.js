import products from "../../Data"
import './Discription.css'


function Discription(props) {
  return (
    <div className="detailBox">
      <div>
        <img src={"https://codingapple1.github.io/shop/shoes" + (Number(props.id) + 1)+ ".jpg"} width="500px" height="400px" alt="" />
      </div>
      <div>
        <h1>{products[props.id].title}</h1>
        <div className="priceBox">
          <p>{products[props.id].price}</p>
        </div>
      </div>
    </div>
  )
}

export default Discription