import MessageList from "../Pages/Chat/MessageList"
import TextList from "../Pages/Chat/TextList"

function Chat() {

  return (
    <div className="chatBox">
        <MessageList />
        <TextList />
    </div>

  )
}

export default Chat