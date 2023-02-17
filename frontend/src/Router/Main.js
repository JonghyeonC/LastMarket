// import Card from "../Components/Card"
// import products from "../Data"
// import Swiper from "../Components/JjimSwiper"
import GoodsListSwiper from "../Components/GoodsListSwiper"
import GoodsList from "../Components/GoodsList"
import axios from "axios"
import { useEffect, useState } from "react"
// import GoodsListCard from "../Components/GoodsListCard"


// axios

function Main() {
  
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

  return (
    <div>
      <div className='container'>
        <div>
          {/* <div>
            <p>여기서</p>
              <GoodsList />
            <p>액시오스</p>
          </div> */}
          {/* <hr /> */}
          <br />
          <div>
            <h1 >{addrs}의 {lifestyles} () 상품</h1>
            <br />
            <div>
              <GoodsListSwiper lifestyles={lifestyles} addrs={addrs} sort="favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
            </div>
          </div>
          <br />
          <br />
          <div>
            <h1>{addrs}에서 {lifestyles}로 () 라이브 중</h1>
            <br />
            <div>
              <GoodsListSwiper lifestyles={lifestyles} addrs={addrs} sort="favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="dealState=DEFAULT&dealState=ONBROADCAST" />
            </div>
          </div>
          <hr />
          <br />
        </div>
        <div>
          <h1>{addrs}에서 NEW!</h1>
          <br />
          <GoodsList addrs={addrs} sort="lastModifiedDateTime,DESC&sort=favoriteCnt" dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
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

export default Main
