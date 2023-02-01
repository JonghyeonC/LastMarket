import products from "../../Data"

function Discription(props) {
  return (
    <div>
      <img src={"https://codingapple1.github.io/shop/shoes" + (Number(props.id) + 1)+ ".jpg"} alt="" />
      <span>
        <div>
          <p>{products[props.id].title}</p>
          <p>{products[props.id].price}</p>
        </div>
      </span>
    </div>
  )
}

export default Discription