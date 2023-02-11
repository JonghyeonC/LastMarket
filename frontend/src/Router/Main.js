// import Card from "../Components/Card"
// import products from "../Data"
// import Swiper from "../Components/JjimSwiper"
import GoodsListSwiper from "../Components/GoodsListSwiper"
import GoodsList from "../Components/GoodsList"
import axios from "axios"
import { useEffect, useState } from "react"

// import GoodsListCard from "../Components/GoodsListCard"
import { useSelector, useDispatch } from 'react-redux'
import { addUserInfo, addToken, addInfo } from '../redux/store'
import { useLocation } from 'react-router-dom';
<<<<<<< HEAD
import { getCookie } from "../Hooks/Cookies"
=======
import Cookies from 'js-cookie'
import jwt_decode from "jwt-decode"

>>>>>>> 013b610 (Fix error)
// axios

function Main() {
  const location = useLocation();
  // 이 부분부터 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다
  const URL = `https://i8d206.p.ssafy.io/api/user`

  const [ lifestyles, setLifestyles ] = useState('')
  const [ addrs, setAddrs ] = useState('')

  const dispatch = useDispatch()

  const cookieValue =  Cookies.get('Authentication');
  // console.log(cookieValue);

  const getUserInfo = (() => {
    return(
      axios({
        method: "get",
        url: URL,
      })
      .then((res) => {
        console.log(res)
      })
      .catch((res) => {
        console.log("실패")
      })
    )
  })
  
  useEffect(() => {
    getUserInfo()
  },[])

  useEffect(() => {
    dispatch(addToken(cookieValue))
  }, [cookieValue])
  
  let reduxData = useSelector((state) => {return state})
  // console.log(reduxData.token)

  if (reduxData.token) {
      dispatch(addInfo(jwt_decode(reduxData.token)))
  }

  console.log('리덕스')
  console.log(reduxData.userInfo)
  
  // 이 부분까지 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다
  


  return (
    <div>
      <div className='container'>
        <div>
          {/* <div>
            <p>여기서</p>
              <GoodsList />
            <p>액시오스</p>
          </div> */}
          {/* <hr /> */}
          <br />
          <div>
            <h1 >{addrs.split(' ')[2]}의 <img className="ListTitleLetterPic" src="letter_HOT.png" alt="HOT" /> 한 <img className="ListTitleLetterPic" src={"letter_"+`${lifestyles}`+".png"} alt="lifestyles" /> 상품</h1>
            <br />
            <div>
              <GoodsListSwiper lifestyles={'lifestyle='+lifestyles} addrs={'&location='+addrs} sort="&sort=favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="&dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
            </div>
          </div>
          <br />
          <br />
          <div>
            <h1>{addrs.split(' ')[2]}에서 <img className="ListTitleLetterPic" src={"letter_"+`${lifestyles}`+".png"} alt="lifestyles" /> <img className="ListTitleLetterPic" src="letter_LIVE.png" alt="LIVE" />  중</h1>
            <br />
            <div>
              <GoodsListSwiper lifestyles={'lifestyle='+lifestyles} addrs={'&location='+addrs} sort="&sort=favoriteCnt,DESC&sort=lastModifiedDateTime,DESC" dealState="&dealState=DEFAULT&dealState=ONBROADCAST" />
            </div>
          </div>
          <hr />
          <br />
        </div>
        <div>
          <h1>{addrs.split(' ')[2]}의 <img className="ListTitleLetterPic" src="letter_NEW.png" alt="NEW" />  <img className="ListTitleLetterPic" src={"letter_"+`${lifestyles}`+".png"} alt="lifestyles" /></h1>
          <br />
          <GoodsList lifestyles={'lifestyle='+lifestyles} addrs={'&location='+addrs} sort="&sort=lastModifiedDateTime,DESC&sort=favoriteCnt" dealState="&dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST" />
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

export default Main
