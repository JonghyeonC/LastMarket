import MessageList from "../Pages/Chat/MessageList"
import MessageRoom from "../Pages/Chat/MessageRoom"
import LiveChat from "../Chat"
function Chat() {

  return (
    <div className="chatBox">
        {/* <MessageList />
        <MessageRoom /> */}
        <LiveChat />
    </div>

  )
}

export default Chat