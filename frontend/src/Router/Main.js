import Card from "../Components/Card"
import products from "../Data"

function Main() {

  return (
    <div>
      <div className='container'>
      <h1>'진평동'에서 미니멀 라이프라면</h1>
        <div className='row'>
        {
          products.map((product, i) => {
            return (
              <Card key={i} product={product} i={i} />
            )
          })
        }
        </div>
        <h1>'진평동'에서 요리 중에서 라이브방송</h1>
        <div className='row'>
        {
          products.map((product, i) => {
            return (
              <Card key={i} product={product} i={i} />
            )
          })
        }
        </div>
        <h1>'진평동'에서 NEW</h1>
        <div className='row'>
        {
          products.map((product, i) => {
            return (
              <Card key={i} product={product} i={i} />
            )
          })
        }
        </div>
      </div>
    </div>
  )
}

export default Main
