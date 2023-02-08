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
  
  // 이 부분부터 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다
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

  // 이 부분까지 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다


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
            <h1 >{addrs}의 Hot한 {lifestyles}라이프 상품</h1>
            <br />
            <div>
              <GoodsListSwiper lifestyles={lifestyles} addrs={addrs} sort="favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
            </div>
          </div>
          <br />
          <br />
          <div>
            <h1>{addrs}에서 {lifestyles}라이프 Live 중</h1>
            <br />
            <div>
              <GoodsListSwiper lifestyles={lifestyles} addrs={addrs} sort="favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="dealState=DEFAULT&dealState=ONBROADCAST" />
            </div>
          </div>
          <hr />
          <br />
        </div>
        <div>
          <h1>{addrs}의 NEW {lifestyles}라이프!</h1>
          <br />
          <GoodsList lifestyles={lifestyles} addrs={addrs} sort="lastModifiedDateTime,DESC&sort=favoriteCnt" dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
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
