import { useEffect, useState } from 'react'
import axios from 'axios'
import { useParams } from 'react-router-dom'

function Search() {
  
  const { result } = useParams()
  const [results, setResults] = useState('')
  
  function searchApi() {
    const url = `https://c1d1e486-0a6a-449a-a1b5-d12ec8679d3b.mock.pstmn.io/search?category=""&word="ham"`

    axios.get(url)
    .then((res) => {
      setResults(res.data)
      console.log(results)
      // console.log("성공")
    })
    .catch((res) => {
      console.log("실패")
    })
  }

  useEffect(() => {
    searchApi()
  }, [result])



  return (
    <div>
      {
        result
      }
    </div>
  )
}

export default Search