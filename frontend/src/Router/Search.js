import { useEffect, useState } from 'react'
// import axios from 'axios'
import { useParams } from 'react-router-dom'
import SearchGoodsList from '../Components/SearchGoodsList'

import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
// import SplitButton from 'react-bootstrap/SplitButton';



// function DropDirectioExample() {

//   const [tabs, setTabs] = useState('')
//   const [tabsname, setTabsName] = useState('라이브')

//   function setLive() {
//     setTabs('"favoriteCnt,DESC&sort=lastModifiedDateTime,DESC"$dealState="dealState=DEFAULT&dealState=ONBROADCAST"')
//     setTabsName('라이브')
//   }
//   function setJjim() {
//     setTabs('"favoriteCnt,DESC&sort=lastModifiedDateTime,DESC"&dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST"')
//     setTabsName('찜순')
//   }
//   function setNew() {
//     setTabs('"lastModifiedDateTime,DESC&sort=favoriteCnt"&dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST"')
//     setTabsName('최신순')
//   }

//   return (
//     <>
//       <div className="mb-2">
//         {['down'].map(
//           (direction) => (
//             <DropdownButton
//               // as={ButtonGroup}
//               key={direction}
//               id={`dropdown-button-drop-${direction}`}
//               drop={direction}
//               variant="secondary"
//               title={` ${tabsname} `}
//             >
//               <Dropdown.Item eventKey="1" onClick={() => setLive()}>라이브</Dropdown.Item>
//               <Dropdown.Item eventKey="2" onClick={() => setJjim() }>찜순</Dropdown.Item>
//               <Dropdown.Item eventKey="3" onClick={() => setNew()}>최신순</Dropdown.Item>
//             </DropdownButton>
//           ),
//         )}
//       </div>

//     </>
//   );
// }


function Search() {
  
  const { result } = useParams()
  // const [results, setResults] = useState('')


  // useEffect(() => {
  //   initialSet()
  // }, [result])
  
  
  // function searchApi() {
  //   const url = `https://i8d206.p.ssafy.io/api/product?category=&lifestyle=&location=&sort=${tabs}&page=&keword=`

  //   axios.get(url)
  //   .then((res) => {
  //     setResults(res.data)
  //     console.log(res)
  //     console.log(`${tabsname} 받는것 성공`)
  //     console.log(results)
  //     // console.log("성공")
  //   })
  //   .catch((res) => {
  //     console.log("실패")
  //   })
  // }

  // useEffect(() => {
  //   searchApi()
  // }, [result])


  const [tabs, setTabs] = useState('favoriteCnt,DESC&sort=lastModifiedDateTime,DESC&dealState=DEFAULT&dealState=ONBROADCAST')
  const [tabsname, setTabsName] = useState('라이브')

  function setLive() {
    setTabs('favoriteCnt,DESC&sort=lastModifiedDateTime,DESC&dealState=DEFAULT&dealState=ONBROADCAST')
    setTabsName('라이브')
  }
  function setJjim() {
    setTabs('favoriteCnt,DESC&sort=lastModifiedDateTime,DESC&dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST')
    setTabsName('찜순')
  }
  function setNew() {
    setTabs('lastModifiedDateTime,DESC&sort=favoriteCnt&dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST')
    setTabsName('최신순')
  }

  function DropDirectioExample() {

    // const [tabs, setTabs] = useState('"favoriteCnt,DESC&sort=lastModifiedDateTime,DESC"$dealState="dealState=DEFAULT&dealState=ONBROADCAST"')
    // const [tabsname, setTabsName] = useState('라이브')
  
    // function setLive() {
    //   setTabs('"favoriteCnt,DESC&sort=lastModifiedDateTime,DESC"$dealState="dealState=DEFAULT&dealState=ONBROADCAST"')
    //   setTabsName('라이브')
    // }
    // function setJjim() {
    //   setTabs('"favoriteCnt,DESC&sort=lastModifiedDateTime,DESC"&dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST"')
    //   setTabsName('찜순')
    // }
    // function setNew() {
    //   setTabs('"lastModifiedDateTime,DESC&sort=favoriteCnt"&dealState="dealState=DEFAULT&dealState=ONBROADCAST&dealState=AFTERBROADCAST"')
    //   setTabsName('최신순')
    // }

  
    return (
      <>
        <div className="mb-2">
          {['down'].map(
            (direction) => (
              <DropdownButton
                // as={ButtonGroup}
                key={direction}
                id={`dropdown-button-drop-${direction}`}
                drop={direction}
                variant="secondary"
                title={` ${tabsname} `}
              >
                <Dropdown.Item eventKey="1" onClick={setLive}>라이브</Dropdown.Item>
                <Dropdown.Item eventKey="2" onClick={setJjim}>찜순</Dropdown.Item>
                <Dropdown.Item eventKey="3" onClick={setNew}>최신순</Dropdown.Item>
              </DropdownButton>
            ),
          )}
        </div>
  
      </>
    );
  }

  useEffect(() => {
    DropDirectioExample()
  }, [result])


  return (
    <div>
      {
        <div>
          {tabs}
          {result}
          <div className='SearchDropDownBox'>
            <DropDirectioExample />
          </div>
          <div>
            <SearchGoodsList tabs={tabs} result={result} />
          </div>
        </div>
      }
    </div>
  )
}

export default Search