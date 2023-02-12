import { useState, useEffect } from "react"
import ProfileGoodsList from '../Components/ProfileGoodsList'


function TabContent({tab}) {

  let [fade, setFade] = useState('')

  useEffect(() => {
    let a = setTimeout(() => { setFade('end') }, 10)
    // setFade('end')

    return () => {
      clearTimeout(a)
      setFade('')
    }
  }, [tab])
  return (<div className={`start ${fade}`}>
    { 
    [
    <div>
      <ProfileGoodsList tab={tab} />
    </div>, 
    <div>
      <ProfileGoodsList tab={tab} />
    </div>,
    <div>
      <ProfileGoodsList tab={tab} />
    </div>,
    <div>
      <ProfileGoodsList tab={tab} />
    </div>, 
    ][tab] 
    }
  </div>)
}

export default TabContent