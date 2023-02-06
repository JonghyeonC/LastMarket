// import Card from "../Components/Card"
// import products from "../Data"
// import Swiper from "../Components/JjimSwiper"
import GoodsListSwiper from "../Components/GoodsListSwiper"
import GoodsList from "../Components/GoodsList"
import axios from "axios"
import { useEffect } from "react"
// import GoodsListCard from "../Components/GoodsListCard"


// axios

function Main() {
  
  const URL = `https://i8d206.p.ssafy.io/api/user`

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

  console.log(1)
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
          <h1>('진평동')의 (미니멀 라이프) () 상품</h1>
          <br />
          <div>
            <GoodsListSwiper />
          </div>
          <br />
          <br />
          <h1>('진평동')에서 (미니멀 라이프)로 () 라이브 중</h1>
          <br />
          <div>
            <GoodsListSwiper />
          </div>
          <hr />
          <br />
        </div>
        <div>
          {/* <GoodsListCard /> */}
        </div>
        <div>
          <h1>('진평동')에서 NEW!</h1>
            <GoodsList />
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
