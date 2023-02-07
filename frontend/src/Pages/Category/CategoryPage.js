import { useParams } from "react-router-dom"

import GoodsList from "../../Components/GoodsList"
import GoodsListSwiper from "../../Components/GoodsListSwiper"

function CategoryPage() {
  const { name } = useParams()

  return(
    <div>
      {/* {name} */}
      <div className='container'>
        <div>
          <br />
          <h1>('진평동')의 (미니멀 라이프) ({name}) 상품</h1>
          <br />
          <div>
            <GoodsListSwiper name={name} sort1="favoriteCnt" sort2="lastModifiedDateTime,DESC" />
          </div>
          <br />
          <br />
          <h1>('진평동')에서 (미니멀 라이프)로 ({name}) 라이브 중</h1>
          <br />
          <div>
            <GoodsListSwiper name={name} dealState1="default" dealState2="onbroadcast" />
          </div>
          <hr />
          <br />
        </div>
        <div>
          {/* <GoodsListCard /> */}
        </div>
        <div>
          <h1>('진평동')에서 새로운 ({name})!</h1>
            <GoodsList name={name} sort1="lastModifiedDateTime,DESC" sort2="favoriteCnt" />
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