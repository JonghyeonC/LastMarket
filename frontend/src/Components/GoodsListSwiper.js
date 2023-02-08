// import React, { useState, useEffect } from 'react';
// import axios from 'axios';

// const GoodsList = () => {
//   const [data, setData] = useState([]);

//   useEffect(() => {
//     const fetchData = async () => {
//       const result = await axios.get('https://63849468-1da2-48a0-ab71-cde66c0c193b.mock.pstmn.io');
//       setData(result.data);
//     };
//     fetchData();
//   }, []);

//   return (
//     <ul>
//       {data.map(products => (
//         <li key={products.id}>{products.title}</li>
//       ))}
//     </ul>
//   );
// };

// export default GoodsList;


import { Swiper, SwiperSlide } from "swiper/react"; // basic
import SwiperCore, { Navigation, Pagination } from "swiper";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";

import { useEffect, useState } from 'react'
import axios from 'axios'
// import { useParams } from 'react-router-dom'
import GoodsListCard from './GoodsListCard'

function GoodsListSwiper(props) {
  
  // const { filter } = useParams()
  const [ products, setProducts ] = useState([])
  
  function GoodsListApi() {
    // const url = `https://63849468-1da2-48a0-ab71-cde66c0c193b.mock.pstmn.io/products?category=&location=&sort=&dealState=&page=&`
    const url = `https://i8d206.p.ssafy.io/api/product?${props.lifestyles}${props.addrs}${props.sort}${props.dealState}`

    axios.get(url)
    .then((res) => {
      setProducts(res.data.content)
      // console.log(res)
      console.log(`${props.name} products 받는건 성공`)
    })
    .catch((res) => {
      console.log("실패")
      console.log(url)
    })
  }

  useEffect(() => {
    GoodsListApi()
  }, [props.name])

  console.log(products)


  // const title = products.map((product) => {
  //   return product.title
  // })

  // console.log(title)
  


  return (
    // <div>
    //   {
    //     products.map((productData, i) => {
    //       return (
    //         <GoodsListCard products={productData} productId={i} />
    //       )
    //     })
    //   }
    // </div>
    <Swiper
    spaceBetween={150}
    slidesPerView={5}
    scrollbar={{ draggable: true }}
    navigation
    pagination={{ clickable: true }}
    breakpoints={{
      1200: {
        slidesPerView: 5,
      },
      900: {
        slidesPerView: 4,
      },
      768: {
        slidesPerView: 3,
      },
      576: {
        slidesPerView: 2,
      },

    }}
    >
      <div className='row'>
        {
          products.map((product) => {
            return (
              <SwiperSlide>
                <GoodsListCard product = {product}/>
              </SwiperSlide>   
            )
          })
        }
      </div>
    </Swiper>
    // <div className='row'>
    //   {
    //     products.map((product) => {
    //       return <GoodsListCard product = {product}/>
    //     })
    //   }
    // </div>
    
  )

}

export default GoodsListSwiper