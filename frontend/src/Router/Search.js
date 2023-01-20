import { useEffect, useState } from 'react'
import axios from 'axios'
import { useParams } from 'react-router-dom'

function Search() {
  
  let { result } = useParams()
  let [results, setResults] = useState('')
  
  function searchApi() {
    const url = `https://d090430e-7192-49f0-8612-c0e6dbe175a9.mock.pstmn.io/search?category=""&word="ham"`

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