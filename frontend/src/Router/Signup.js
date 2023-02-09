import "./Signup.css"
import { FaAngleDown } from "react-icons/fa"
import { useEffect, useState } from "react"
import axios from "axios"
import { useNavigate } from "react-router-dom"

function Signup() {
  const navigate = useNavigate()
  const [life, setLife] = useState([])
  
  let [ nickName, setNickName ] = useState('')
  let [ lifestyle, setlifeStyle ] = useState('')
  let [ location, setLocation ] = useState('')

  const category = (() => {
    return (
      axios({
        method: 'get',
        url: `https://i8d206.p.ssafy.io/api/lifestyles`
      })
      .then((res) => {
        setLife(res.data.lifestyles)
      })
      )
    })
    
    const sendInfor = (() => {
      return (
        axios({
          method : 'post',
          url : `https://i8d206.p.ssafy.io/api/user`,
          data : {
            "nickname" : nickName,
            "lifestyle" : lifestyle,
            "categories" : [],
            "addr" : String(`"${location.split(' ')[0]} ${location.split(' ')[1]} ${location.split(' ')[2]}"`),
          },
          withCredentials: true,
        })
        .then((res) => {
          console.log(res)
          navigate('/')
        })
        .catch((res) => {
        navigate('/signup')
      })
      )
    })
    
    const options = {
      enableHighAccuracy: true,
      timeout: 5000,
      maximumAge: 0
    };

    console.log(typeof(location.split(' ')[0]))
    console.log(location.split(' ')[1])
    console.log(location.split(' ')[2])
    
    function success(position) {
      //좌표를 알아낼 수 있는데, 여기서 알아낸 좌표를 kakaoAPI url에 사용할 것이다.
      // console.log('위도 : ' + position.coords.latitude); 
    // console.log('경도: ' + position.coords.longitude);
  };
  
  function error(err) {
    console.warn('ERROR(' + err.code + '): ' + err.message);
  };
  
  navigator.geolocation.getCurrentPosition(success, error, options);

  function onGeoOk(position){
    const lat = position.coords.latitude;
    const lon = position.coords.longitude;
    
    //kakao REST API에 get 요청을 보낸다.
    //파라미터 x,y에 lon,lat을 넣어주고 API_KEY를 Authorization헤더에 넣어준다.
    axios.get(`https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lon}&y=${lat}&input_coord=WGS84`
    ,{headers:{Authorization:`KakaoAK fb48a90139fb5e563023ad03ca49e216`}}
    )
    .then(res=>{
        setLocation(res.data.documents[0].address.address_name)
        // dispatch(changeRegion(res.data.documents[0].address.region_1depth_name))
        // dispatch(changeCity(res.data.documents[0].address.region_2depth_name)) 
    }
    ).catch(e=>console.log(e))
    }

    function onGeoError(){
        alert("위치권한을 확인해주세요");
    }

    //navigator.geolocation.getCurrentPosition(위치받는함수, 에러났을때 함수)
    navigator.geolocation.getCurrentPosition(onGeoOk,onGeoError)


  useEffect(() => {
    category();
  }, [])

  console.log(nickName)
  console.log(lifestyle)
  // console.log(location)

  return (
    <div className='Signup'>
      <h1>회원가입</h1>
      <p>당신의 라이프스타일을 알려주세요!</p>
      <div className='nameWrap'>
        <p className="labelBox">회원님의 닉네임을 알려주세요!</p>
        <input type="text" className="nickNameInput" onChange={(e) => setNickName(e.target.value)}/>
      </div>
      <br />
      <div className='nameWrap'>
        <p className="labelBox">회원님은 어디에 있나요?</p>
        <div className='signupbox'>
          <span>{location.split(' ')[0]} </span>
          <span>{location.split(' ')[1]} </span>
          <span>{location.split(' ')[2]}</span>
        </div>
      </div>
      <br />
      <div className='nameWrap'>
        <p className="labelBox">회원님의 라이프스타일은?</p>
        <div className='signupbox'>
          <input id="dropdown2" type="checkbox" />
          <label className='dropdownLabel1' for="dropdown2">
            {
              life ?
              lifestyle :
              <div>어떤 것에 관심이 있는지 선택해주세요</div>
            }
            <FaAngleDown className='caretIcon' />
          </label>
          <div className='content'>
            <ul>
              {
                life.map((style) => {
                  return (
                    <li onClick={() => {setlifeStyle(style)}}>{style}</li>
                  )
                })
              }
            </ul>
          </div>
        </div>
      </div>
      <br />
      <div className="sendBtn">
        <button onClick={sendInfor} >제출</button>
      </div>
    </div>
  )
}

export default Signup