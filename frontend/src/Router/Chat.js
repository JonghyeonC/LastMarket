import MessageList from "../Pages/Chat/MessageList"
import MessageRoom from "../Pages/Chat/MessageRoom"

function Chat() {

  return (
    <div className="chatBox">
        <MessageList />
        <MessageRoom />
    </div>

  )
}

export default Chat