import ProfileComponent from "../Components/ProfileComponent";
import LiveChat from "../Chat";


function Profile() {

  return (
    <div>
      <div className="profile_Compbox">
        <div className="profile_Profile_Com">
          <ProfileComponent />
        </div>
        <div className="profile_Chat_Com">
          <LiveChat />
        </div>
      </div>
    </div>
  )
}

export default Profile