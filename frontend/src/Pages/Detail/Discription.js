import { useEffect, useState } from 'react'
import products from "../../Data"
import './Discription.css'
import axios from 'axios'


function Discription(props) {
  
  const [ productDetail, setProductDetail ] = useState([])
  const [ detailURIS, setDetailURIS ] = useState([])
  function DiscriptionApi() {
    
    const url = `https://i8d206.p.ssafy.io/api/product/${props.id}`
  
    axios.get(url)
    .then((res) => {
      setProductDetail(res.data)
      setDetailURIS(res.data.imgURIs)
      // console.log(res.data.imgURIs)
      console.log('success')
      
    })
    .catch((res) => {
      console.log('Failed')
    })
  }

  useEffect(() => {
    DiscriptionApi()
  }, [])

  console.log(productDetail)

  return (
    <div className="detailBox">
      <div>
        {/* <img src={productDetail.imgURIs} width="500px" height="400px" alt="DetailImg" /> */}
      </div>
      <div>
        <h1>{productDetail.title}</h1>
        <div className="priceBox">
          <p>{productDetail.startingPrice}</p>
        </div>
        <div>
          {
            detailURIS.map((imgURI) => {
              return (
                <img src={imgURI} width="500px" height="400px" alt="DetailImg" />
              )
            })
          }
        </div>
      </div>
      {/* <div>
        <img src={"https://codingapple1.github.io/shop/shoes" + (Number(props.id) + 1)+ ".jpg"} width="500px" height="400px" alt="" />
      </div>
      <div>
        <h1>{products[props.id].title}</h1>
        <div className="priceBox">
          <p>{products[props.id].price}</p>
        </div>
      </div> */}
    </div>
  )
}

export default Discription