// import { useLocation, useParams } from 'react-router-dom';
// import { useSelector } from 'react-redux';
// import { useEffect, useState } from 'react';

// function MessageRoom() {

//   const expertId = localStorage.getItem('expertId');
//   const socket = useSelector(state => state.socketStorage.socket);
//   const { pathname } = useLocation();
//   const roomId = useParams().room_index;
//   const [participantId, setParticipantId] = useState(-1);
//   const [chats, setChats] = useState([]);

//   const getTimeString = (createdAt) => { // 시간을 출력 포맷으로 바꿔주는 함수
//     const isCreated = new Date(createdAt);
//     isCreated.setHours(isCreated.getHours() - 9);
//     const hour = new Date(isCreated).getHours();
//     const minute = new Date(isCreated).getMinutes();
//     const hourValue = hour < 10 ? `0${hour}` : hour;
//     const minuteValue = minute < 10 ? `0${minute}` : minute;
//     const ampm = hour < 12 ? 'am' : 'pm';
//     const timeValue = `${hourValue}:${minuteValue} ${ampm}`;
  
//     return timeValue;
//   }

//   useEffect(() => { // 방 바꾸기, 변경될 떄마다 새로운 fetch 받아와야 한다.
//     const joinExpertEmitObject = {
//         room_id: roomId,
//         user_id: expertId, 
//     }
//     socket.emit('join-expert', joinExpertEmitObject);        
//     socket.on('join-expert', (joinObj) => { // 방 하나 골라서 들어가는 이벤트
//         const { result, errmsg } = joinObj;
//         // room에 join시 participant id 저장
//         setParticipantId(result.participant_index);

//         const getChattingObj = {
//             room_id: roomId,
//             participant_id: result.participant_index,
//         }
//         socket.emit('getChatting', getChattingObj);
//     });

//     socket.on('getChatting', (chatObj) => { // 룸에 들어가면, 현재 룸에 쌓여있는 채팅 리스트 가져옴
//         const { result, errmsg } = chatObj;
//         setChats(result);
//     });

//     return () => {
//       const leaveObj = {
//           room_id : roomId,
//       }

//       socket.emit('disconnect-expert', leaveObj);
//       socket.disconnect();
//       };
//     }, [pathname, socket]);
  
//   useEffect(() => {
//     arrivalChat && setChats((prev) => [...prev, arrivalChat]) // 채팅 리스트에 추가
//   }, [arrivalChat]);

//   useEffect(() => {
//       socket.on('message-expert', (chatObj) => { // 메세지 수신
//           const { result, errmsg } = chatObj;
//           setArrivalChat(result);
//       });
//   }, [socket]) // 괄호 안의 변수에 변화가 생기면 useEffect 내부 함수 실행

//   const sendMessage = async () => { 
//     if (!newChatRef.current.value)
//         return;

//     await socket.emit('message-expert', { // 메세지 전송 
//         room_id: roomId,
//         sender_index: participantId,
//         message: newChatRef.current.value,
//     });

//         newChatRef.current.value = '';
//     }

//   return (
//     <div></div>
//     )
//   }

// export default MessageRoom