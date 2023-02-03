import { Swiper, SwiperSlide } from "swiper/react"; // basic
import SwiperCore, { Navigation, Pagination } from "swiper";
import "swiper/css"; //basic
import "swiper/css/navigation";
import "swiper/css/pagination";

import Card from "../Components/Card"
import products from "../Data"


export default () => {
  return (
    <Swiper
      spaceBetween={50}
      slidesPerView={3}
      scrollbar={{ draggable: true }}
      navigation
      pagination={{ clickable: true }}
      breakpoints={{
        768: {
          slidesPerView: 7,
        },
      }}
    >
        <div className='row'>
          {
            products.map((product, i) => {
              return (
                <SwiperSlide>
                  <Card key={i} product={product} i={i} />
                </SwiperSlide>
              )
            })
          }
          </div>
    </Swiper>
  );
};