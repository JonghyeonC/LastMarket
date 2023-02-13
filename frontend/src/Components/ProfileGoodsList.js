import { useEffect, useState } from 'react'
import axios from 'axios'
import GoodsListCard from './GoodsListCard'

function ProfileGoodsList(props) {
  
  const [ tabUrls, setTabUrls ] = useState('trades/sell')
  const [ products, setProducts ] = useState([])

  function TabtoURL() {
    if (`${props.tab}` === '0'){
      return(
        setTabUrls('trades/sell')
      )
    } else if (`${props.tab}` === '1'){
      setTabUrls('reviews')
    } else if (`${props.tab}` === '2'){
      setTabUrls('favorite')
    } else if (`${props.tab}` === '3'){
      setTabUrls('trades/buy')
    }
  }

  useEffect(() => {
    TabtoURL()
  }, [props])
  

  function ProfileGoodsListApi() {
    const url = `https://i8d206.p.ssafy.io/api/${tabUrls}`

    axios.get(url)
    .then((res) => {
      setProducts(res.data.content)
      console.log(res)
      console.log(`products 받기 성공`)
      console.log(url)
    })
    .catch((res) => {
      console.log("실패")
      console.log(url)
    })
  }

  useEffect(() => {
    ProfileGoodsListApi()
  }, [tabUrls])

  console.log(products)

  return (

    <div className='row'>
      {
        products?.map((product) => {
          return <GoodsListCard product = {product}/>
        })
      }
    </div>
    
  )

}

export default ProfileGoodsList