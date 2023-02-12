import { useState, useEffect } from "react"
import products from "../Data"
import Card from "./Card"

function TabContent({tab}) {

  let [fade, setFade] = useState('')

  useEffect(() => {
    let a = setTimeout(() => { setFade('end') }, 10)
    // setFade('end')

    return () => {
      clearTimeout(a)
      setFade('')
    }
  }, [tab])
  return (<div className={`start ${fade}`}>
    { 
    [
    <div>
      <div className="container">
        <div className="row">
          {
            products.map((product, i) => {
              return (
                <Card key={i} product={product} i={i} />
              )
            })
          }
        </div>
      </div>
    </div>, 
    <div>
      <div className="container">
        <div className="row">
          {
            products.map((product, i) => {
              return (
                <Card key={i} product={product} i={i} />
              )
            })
          }
        </div>
      </div>
    </div>, 
    <div>후기</div>][tab] 
    }
  </div>)
}

export default TabContent