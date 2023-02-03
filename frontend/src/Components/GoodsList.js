import { useEffect, useState } from 'react'
import axios from 'axios'
import { useParams } from 'react-router-dom'
import GoodsListCard from './GoodsListCard'

function GoodsList(props) {
  
  const { filter } = useParams()
  // 라이프스타일

  const [ products, setProducts ] = useState([])
  
  function GoodsListApi() {
    // const url = `https://63849468-1da2-48a0-ab71-cde66c0c193b.mock.pstmn.io/products?category=&location=&sort=&dealState=&page=&`
    const url = `https://i8d206.p.ssafy.io/api/product?category=${props.name}&lifestyle=&location=&sort=&dealState=&page=&keword=`

    axios.get(url)
    .then((res) => {
      setProducts(res.data.content)
      // console.log(res)
      console.log("products 받는건 성공")
    })
    .catch((res) => {
      console.log("실패")
    })
  }

  useEffect(() => {
    GoodsListApi()
  }, [filter])

  console.log(products)

  return (

    <div className='row'>
      {
        products.map((product) => {
          return <GoodsListCard product = {product}/>
        })
      }
    </div>
    
  )

}

export default GoodsList