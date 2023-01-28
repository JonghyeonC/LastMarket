import Card from "../Components/Card"

function Main() {
  const products = [
    {
      id : 0,
      title : "White and Black",
      price : 120000
    },
  
    {
      id : 1,
      title : "Red Knit",
      price : 110000
    },
  
    {
      id : 2,
      title : "Grey Yordan",
      price : 130000
    },

    {
      id : 3,
      title : "Check Snikers",
      price : 90000
    },
  ]

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
