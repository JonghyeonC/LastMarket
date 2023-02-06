import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

function Category() {
  
  const navigate = useNavigate()

  const URL = `https://i8d206.p.ssafy.io/api/categories`
  const [ categories, setCategories ] = useState([]) 

  const getCategory = (() => {
    return(
      axios({
        method: "get",
        url: URL,
      })
      .then((res) => {
        setCategories(res.data.categories)
      })
      .catch((res) => {
        console.log("실패")
      })
    )
  })

  console.log(categories)

  useEffect(() => {
    getCategory()
  }, [])

  return(
    <div>
      {
        categories.map((cate) => {
          return(
            <p className="cate_name" onClick={() => {navigate(`/category/${cate}`)}}>{cate}</p>
          )
        })
      }
    </div>
  )
}


export default Category