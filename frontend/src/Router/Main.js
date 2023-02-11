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
import Cookies from 'js-cookie'
// import jwt_decode from "jwt-decode"

// axios

function Main() {
  const location = useLocation();
  // 이 부분부터 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다
  const URL = `https://i8d206.p.ssafy.io/api/user`

  const [ lifestyles, setLifestyles ] = useState('')
  const [ addrs, setAddrs ] = useState('')

  const dispatch = useDispatch()

  const getUserInfo = (() => {
    return(
      axios({
        method: "get",
        url: URL,
      })
      .then((res) => {
        // console.log(res)
        console.log('유저정보 들어옴')
        // console.log(res.data)
        setLifestyles(res.data.lifestyles)
        setAddrs(res.data.addr)
        dispatch(addUserInfo(res))
      })
      .catch((res) => {
        console.log("실패")
      })
    )
  })

  // let jwt_token = location.search.substring(7)
  
  useEffect(() => {
    getUserInfo()
    // dispatch(addToken(location?.search.substring(7)))
    // dispatch(addInfo(jwt_decode(jwt_token)))
  },[])
  
  // console.log('리덕스')
  // let reduxData = useSelector((state) => {return state})
  // console.log(reduxData.userInfo)
  
  // 이 부분까지 유저 정보 axios 입니다. redux 사용시 대체할 수 있습니다

  // const getCookie = (name) => {
  //   const cookies = document.cookie.split(';');
  //   const cookie = cookies.find((c) => c.trim().startsWith(`${name}=`));
  //   if (!cookie) return undefined;
  //   return cookie.split('=')[1];
  // };
  
  const cookieValue =  Cookies.get('cookieName');
  console.log(cookieValue);

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
