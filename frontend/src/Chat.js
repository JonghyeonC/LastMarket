import { useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

function LiveChat() {

    const msg_send_btn = useRef()
    const inputBox = useRef()
    // const chat_log = useRef()
    
    const [talk, setTalk] = useState('')
    const [chat_log, setChat_log] = useState([''])
    const [chat, setChat] = useState('')

    const socket = new SockJS("https://i8d206.p.ssafy.io/api/ws");
    console.log(socket)
    const stomp_client = Stomp.over(socket);
    
    stomp_client.connect({}, () => {
        console.log("connection successe")
        stomp_client.subscribe("/exchange/chat.exchange/room.123", (message) => {
            // addChatLog(JSON.parse(message.body));
            // console.log(JSON.parse(message.body))
            addChatLog(message)
        });
    })
    
    function sendMessage() {
        const msg = {
            "chatType": "CHAT ",
            "seller": "seller",
            "buyer": "buyer",
            "sender": "seller",
            "roomKey": "123",
            "message": talk
        }
        return(
            stomp_client.send("/send/room.123", {}, JSON.stringify(msg))
        )
    }

    function addChatLog(msg) {
        let talks = JSON.parse(msg.body)
        // console.log(talks.message)
        if (talks.sender === "seller") {
            // chat.style.color = "red";
        }
        setChat_log(chat_log.concat(talks.message))
    }

    console.log(12)
    console.log(chat_log)

    return (
        <div>
            {/* <div ref={chat_log}></div> */}
            {
                chat_log.map((logs) => {
                    return <div>{logs}</div>
                })
            }
            <input type="text" ref={inputBox} placeholder="채팅을 입력해주세요!!" onChange={(e) => setTalk(e.target.value)} onKeyPress={(e) => { if (e.key === 'Enter') {sendMessage()}}} />
                <br />
            <button ref={msg_send_btn} onClick={sendMessage} >send</button>
                <br />
        </div>
    )
}

export default LiveChat