import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

function Category() {
  
  // 이 부분부터 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다

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

  // 이 부분까지 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다

  
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