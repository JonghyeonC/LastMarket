import { useEffect, useState } from 'react'
// import products from "../../Data"
// import DeleteGood from "./DeleteGood"
import './Discription.css'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import Cookies from 'js-cookie'
import jwt_decode from "jwt-decode"

import Button from 'react-bootstrap/Button';

import { Swiper, SwiperSlide } from "swiper/react"; // basic
import SwiperCore, { Navigation, Pagination, Scrollbar, Autoplay } from "swiper";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";
import 'swiper/css/scrollbar';


function Discription(props) {
  let reduxData = useSelector((state) => {return state})

  const [ productDetail, setProductDetail ] = useState([])
  const [ detailURIS, setDetailURIS ] = useState([])
  const [ isBrod, setIsBrod ] = useState("1")
  
  const cookieValue =  Cookies.get('Authentication');
  
  let userDetail 

  if (cookieValue) {
    userDetail = jwt_decode(cookieValue)
  }
  console.log(userDetail)

  function DiscriptionApi() {
    
    const url = `https://i8d206.p.ssafy.io/api/product/${props.id}`

    axios.get(url)
    .then((res) => {
      setProductDetail(res.data)
      setDetailURIS(res.data.imgURIs)
      console.log(res.data.imgURIs)
      console.log('success')
      
    })
    .catch((res) => {
      console.log('Failed')
    })
  }
  
  useEffect(() => {
    DiscriptionApi()
  }, [])


  // const [ MainImg, setIMainImg ] = useState(detailURIS[0])

  // function BigImg(imgURI){
  //   return(
  //     setIMainImg(imgURI)
  //   )
  // }
  const [ MainImgIndex, setMainImgIndex ] = useState(0)

  function MainImg(index){
    return(
      setMainImgIndex(index)
    )
  }
  // useEffect(() => {
  //   MainImg()
  // }, [])
  
  const navigate = useNavigate()

  function DeleteGood(){

      return(
        axios({
          method: 'delete',
          url: `https://i8d206.p.ssafy.io/api/product/${props.id}`
        })
        .then((res) => {
          console.log('삭제 post 완료')
        })
        .catch((res) => {
          console.log('삭제 post 실패')
        })
      )
  
  }
  
  const favorite = (() => {
    return(
      axios({
        method: 'post',
        url: `https://i8d206.p.ssafy.io/api/favorite/${props.id}`
      })
      .then((res) => {
        console.log(res.data)
      })
    )
  })

  const Delete = (() => {
    return(
      DeleteGood(),
      navigate('/')
    )
  })

  const onBrodcast = (() => {
    return (
      axios({
        method: 'get',
        url: `https://i8d206.p.ssafy.io/api/product/${productDetail.productId}/broadcast`
      })
    )
  })

  console.log(productDetail)

  return (
    <div>
      <div className="decriptionBox">
        <div className='imagesBox'>
          <div>
            <img src={detailURIS[MainImgIndex]} alt="MainImg" width='500px' height="400px" />
          </div>
          <br />
          <Swiper
            className='SwiperBox'
            modules={[Navigation, Pagination, Scrollbar, Autoplay]}
            spaceBetween={15}
            slidesPerView={5}
            // scrollbar={{ draggable: true }}
            navigation
            pagination={{ clickable: true }}
            autoplay={{ delay: 3000 }}
            // breakpoints={{
            //   1200: {
            //     slidesPerView: 1,
            //   },
              // 900: {
              //   slidesPerView: 1,
              // },
              // 768: {
              //   slidesPerView: 1,
              // },
              // 576: {
              //   slidesPerView: 1,
              // },

            // }}
          >
            <div className='row'>
              {
                detailURIS.map((imgURI, index) => {
                  return (
                    <SwiperSlide>
                      <img className='Small_Img' src={imgURI} alt="DetailImg" />
                      {/* onClick={MainImg(index)} */}
                    </SwiperSlide>
                  )
                })
              }
            </div>
          </Swiper>
        </div>
        <br />
        <div className='detailBox'>
          <h1>{productDetail.title}</h1>
          {
            productDetail.sellerId === userDetail?.id ?
            null
            :
            <div className='likeBtn'>
              {
                productDetail.favorite === true ?
                <div>찜 O</div> :
                <div>찜 X</div>
              }
              <div>{productDetail.favoriteCnt}</div>
              <button onClick={favorite}>하트</button>
            </div>
          }
          <div className="priceBox">
            <div>{ 
              <p>{productDetail.startingPrice}</p> ? 
              <p>{productDetail.startingPrice}</p> : 
              <p>{productDetail.instantPrice}</p>}
            </div>
          </div>
          <br />
          <div className='profileBox'>
            {
              <img src={productDetail.profile} alt="프로필사진" /> !== "" ?
              <img src={productDetail.profile} alt="프로필사진" width="100px" height="100px" /> :
              <img src="profile_icon.png" width="150px" height="150px" alt="" />
            }
            <div>
              <h3>{productDetail.sellerNickname}</h3>
              <br />
              <h5>{productDetail.location}</h5>
            </div>
            <span>
              <Button variant="secondary" onClick={() => (navigate(`/Chat_onetoone/${productDetail.productId}`, { state : {productId : `${productDetail.productId}` , id : `${userDetail?.id}` , sellerId : `${productDetail.sellerId}`}} ))}>채팅</Button>
              {/* <button onClick={() => (navigate(`/Chat_onetoone/${productDetail.productId}`, { state : {productId : `${productDetail.productId}` , id : `${userDetail?.id}` , sellerId : `${productDetail.sellerId}`}} ))}>채팅</button> */}
            </span>
          </div>
          <br />
          <br />
          {
            productDetail.sellerId === userDetail?.id ?
            <div>
              {
                productDetail.dealState === "AFTERBROADCAST" ? 
                null :
                <Button variant="primary" onClick={() => {(navigate(`/live_sell/${productDetail.productId}`, { state : {productId : `${productDetail.productId}` , id : `${userDetail?.id}` , sellerId : `${productDetail.sellerId}`}})) ; onBrodcast()} }>라이브 시작</Button>
              }
            </div>
            :
            <div>
              {
                productDetail.liveTime !== null ?
                (
                  productDetail.dealStates ?
                  <Button variant="success">라이브 종료</Button> :
                  <Button variant="success" onClick={() => (navigate(`/live_buy/${productDetail.productId}`, { state : {productId : `${productDetail.productId}` , id : `${userDetail?.id}` , sellerId : `${productDetail.sellerId}`}}))}>라이브 참여</Button>
                ) :
                null
              }
              <span> </span>
              <Button variant="warning" onClick={() => (navigate(`/Chat_onetoone/${productDetail.productId}`, { state : {productId : `${productDetail.productId}` , id : `${userDetail?.id}` , sellerId : `${productDetail.sellerId}`}} ))}>즉시구매</Button>
              {/* <button onClick={() => (navigate(`/live_buy/${productDetail.productId}`, { state : {productId : `${productDetail.productId}` , id : `${userDetail?.id}` , sellerId : `${productDetail.sellerId}`}}))}>라이브 참여</button>
              <button onClick={() => (navigate(`/Chat_onetoone/${productDetail.productId}`, { state : {productId : `${productDetail.productId}` , id : `${userDetail?.id}` , sellerId : `${productDetail.sellerId}`}} ))}>즉시구매</button> */}
            </div>
          }
        </div>
      </div>
      <br />
      <div className='contentBox'>
        {productDetail.content}
      </div>
    </div>
  )
}

export default Discription