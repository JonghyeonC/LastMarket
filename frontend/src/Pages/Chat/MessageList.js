import io from 'socket.io-client'
import { useDispatch } from 'react-redux'
import { socketStorage } from '../../redux/action'
import { useState, useEffect } from 'react';

function MessageList() {

  const dispatch = useDispatch();
  const [updatedRoom, setUpdatedRoom] = useState(null);
  const [chatRooms, setChatRooms] = useState([]);
  const [roomNumber, setRoomNumber] = useState(-1);
  const socket = io.connect(process.env.REACT_APP_SOCKET_URL);
  dispatch(socketStorage(socket))

  useEffect(() => {
    if (updatedRoom) {
        const newChatRooms = [];
        for (const value of chatRooms) {
            if (value.room_index === updatedRoom.room_index) {
                value.last_chat_message = updatedRoom.last_chat_message;
                value.last_chat_time = updatedRoom.last_chat_time;
                value.not_read_chat = updatedRoom.not_read_chat;
                value.sender_index = updatedRoom.participant_id; 
            }
            newChatRooms.push(value);
        }
        setChatRooms(newChatRooms);
    }
  }, [updatedRoom])

  useEffect(() => {
      socket.on('alert-new-message', (arrived) => { // 새로운 메세지 수신
        const { result, errmsg } = arrived;
        setUpdatedRoom(result);
      })
    }
  );

  return (
    <div>

    </div>
  )
}

export default MessageList