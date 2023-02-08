import { useParams } from "react-router-dom"
import axios from "axios"
import { useEffect, useState } from "react"

import GoodsList from "../../Components/GoodsList"
import GoodsListSwiper from "../../Components/GoodsListSwiper"

function CategoryPage() {
  const { name } = useParams()

  const URL = `https://i8d206.p.ssafy.io/api/user`

  const [ lifestyles, setLifestyles ] = useState('')
  const [ addrs, setAddrs ] = useState('')

  const getUserInfo = (() => {
    return(
      axios({
        method: "get",
        url: URL,
      })
      .then((res) => {
        console.log(res)
        console.log('유저정보 들어옴')
        // console.log(res.data)
        setLifestyles(res.data.lifestyles)
        setAddrs(res.data.addr)
      })
      .catch((res) => {
        console.log("실패")
      })
    )
  })

  // console.log(1)
  useEffect(() => {
    getUserInfo()
  },[])

  return(
    <div>
      {/* {name} */}
      <div className='container'>
        <div>
          <br />
          <h1>{addrs}의 {lifestyles} {name} 상품</h1>
          <br />
          <div>
            <GoodsListSwiper name={name} lifestyles={lifestyles} addrs={addrs} sort="favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
          </div>
          <br />
          <br />
          <h1>{addrs}에서 {lifestyles}로 {name}관련 라이브 중</h1>
          <br />
          <div>
            <GoodsListSwiper name={name} lifestyles={lifestyles} addrs={addrs} sort="favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="dealState=DEFAULT&dealState=ONBROADCAST" />
          </div>
          <hr />
          <br />
        </div>
        <div>
          {/* <GoodsListCard /> */}
        </div>
        <div>
          <h1>{addrs}에서 새로운 {name}!</h1>
            <GoodsList name={name} addrs={addrs} sort="lastModifiedDateTime,DESC&sort=favoriteCnt,DESC" dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
          {/* <div className='row'>
          {
            products.map((product, i) => {
              return (
                <Card key={i} product={product} i={i} />
              )
            })
          }
          </div> */}
        </div>
      </div>
    </div>
  )
}

export default CategoryPage