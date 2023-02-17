import { useParams } from "react-router-dom"
import { useNavigate } from 'react-router-dom'
import axios from "axios"
import { useEffect, useState } from "react"

import { useSelector } from 'react-redux';
// import { addToken } from './redux/store'
// import { useDispatch } from 'react-redux';

import GoodsList from "../../Components/GoodsList"
import GoodsListSwiper from "../../Components/GoodsListSwiper"

function CategoryPage() {
  const { name } = useParams()

  // const dispatch = useDispatch()

  // function delCookie() {
  //   dispatch(addToken(null))
  // }

  let navigate = useNavigate()
  let [inputValue, setInputValue] = useState('')
  let reduxData = useSelector((state) => {return state})
  console.log(inputValue)


  const URL = `https://i8d206.p.ssafy.io/api/user`

  const [ lifestyles, setLifestyles ] = useState('')
  const [ addrs, setAddrs ] = useState('')

  const getUserInfo = (() => {
    return(
      axios({
        method: "get",
        url: URL,
      })
      .then((res) => {
        console.log(res)
        console.log('유저정보 들어옴')
        // console.log(res.data)
        setLifestyles(res.data.lifestyles)
        setAddrs(res.data.addr)
      })
      .catch((res) => {
        console.log("실패")
      })
    )
  })

  // console.log(1)
  useEffect(() => {
    getUserInfo()
  },[])

  return(
    <div>
      {/* {name} */}
      <div className='container'>
        <div>
          <br />
          <br />
          <div className="ListTitle">
            {
            reduxData.token ?
              <p>{addrs.split(' ')[2]}의 <b>HOT</b>한 {lifestyles}라이프 {name} 상품</p>
              // <h1>{addrs.split(' ')[2]}의 <img className="ListTitleLetterPic" src="letter_HOT" alt="HOT" /> 한 {lifestyles}라이프 {name} 상품</h1>
            :
              <p>라스트마켓에서 <b>HOT</b>한 {name} 상품</p>
              // <h1>라스트마켓에서 <img className="ListTitleLetterPic" src="letter_HOT.png" alt="HOT" />한 {name} 상품</h1>
            }
          </div>
          <br />
          <div>
            <GoodsListSwiper lifestyles={'category='+name+'&lifestyle='+lifestyles} addrs={'&location='+addrs} sort="&sort=favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="&dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
          </div>
          <br />
          <br />
          <div className="ListTitle">
            {
              reduxData.token ?
              <p>{addrs.split(' ')[2]}에서 {lifestyles}라이프 {name} <b>LIVE</b> 중</p>
              // <h1>{addrs.split(' ')[2]}에서 {lifestyles}라이프 {name} <img className="ListTitleLetterPic" src="letter_LIVE.png" alt="LIVE" />  중</h1>
              :
              <p>라스트마켓에서 {name} <b>LIVE</b> 중</p>
              // <h1>라스트마켓에서 {name} <img className="ListTitleLetterPic" src="letter_LIVE.png" alt="LIVE" /> 중</h1>
            }
          </div>
          <br />
          <div>
            <GoodsListSwiper lifestyles={'category='+name+'&lifestyle='+lifestyles} addrs={'&location='+addrs} sort="&sort=favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="&dealState=DEFAULT&dealState=ONBROADCAST" />
          </div>
          <hr />
          <br />
        </div>
        <div>
          {/* <GoodsListCard /> */}
        </div>
        <div>
          <div className="ListTitle">
            {
              reduxData.token ?
              <p>{addrs.split(' ')[2]}의 <b>NEW</b> {lifestyles}라이프 {name}</p>
              // <h1>{addrs.split(' ')[2]}의 <img className="ListTitleLetterPic" src="letter_NEW.png" alt="NEW" />  {lifestyles}라이프 {name}</h1>
              :
              <p>오늘의 <b>NEW</b> {name} 상품</p>
              // <h1>오늘의 <img className="ListTitleLetterPic" src="letter_NEW.png" alt="NEW" /> {name} 상품</h1>
            }
          </div>
          <br />
          <GoodsList lifestyles={'category='+name+'&lifestyle='+lifestyles} addrs={'&location='+addrs} sort="&sort=lastModifiedDateTime,DESC&sort=favoriteCnt,DESC" dealState="&dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
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