import { useParams } from "react-router-dom"

function CategoryPage() {
  const { name } = useParams()
  return(
    <div>
      {name}
    </div>
  )
}

export default CategoryPage