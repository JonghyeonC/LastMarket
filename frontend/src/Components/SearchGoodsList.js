import { useEffect, useState } from 'react'
import axios from 'axios'
// import { useParams } from 'react-router-dom'
import GoodsListCard from './GoodsListCard'

function SearchGoodsList(props) {
  
  // const { filter } = useParams()
  // 카테고리

  const [ products, setProducts ] = useState([])
  
  function SearchGoodsListApi() {
    // const url = `https://63849468-1da2-48a0-ab71-cde66c0c193b.mock.pstmn.io/products?category=&location=&sort=&dealState=&page=&`
    const url = `https://i8d206.p.ssafy.io/api/product?category=&lifestyle=&location=&sort=${props.tabs}&page=&keyword=${props.result}`

    axios.get(url)
    .then((res) => {
      console.log(`${props.result} 들어옴`)
      console.log(`${props.tab} 들어옴`)
      setProducts(res.data.content)
      // console.log(res)
      console.log(`products 받기 성공`)
    })
    .catch((res) => {
      console.log("실패")
    })
  }

  useEffect(() => {
    SearchGoodsListApi()
  }, [props.name])

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

export default SearchGoodsList