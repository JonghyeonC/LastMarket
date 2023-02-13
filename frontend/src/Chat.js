import { useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import './Chat.css'

function LiveChat(props) {

    const msg_send_btn = useRef()
    const inputBox = useRef()
    
    const [talk, setTalk] = useState('')
    const [chat_log, setChat_log] = useState([''])
    const [ logs, setLogs ] = useState([''])

    const socket = new SockJS("https://i8d206.p.ssafy.io/api/ws");
    console.log(socket)
    const stomp_client = Stomp.over(socket);
    
    stomp_client.connect({}, () => {
        console.log("connection successe")
        stomp_client.subscribe(`/exchange/chat.exchange/room.${props.productId}`, (message) => {
            // addChatLog(JSON.parse(message.body));
            // console.log(JSON.parse(message.body))
            addChatLog(message)
        });
    })
    
    function sendMessage() {
        const msg = {
            "chatType": "CHAT",
            "seller": `${props.sellerId}`,
            "buyer": `${props.id}`,
            "sender": `${props.id}`,
            "roomKey": `${props.productId}`,
            "message": talk
        }
        return(
            stomp_client.send(`/send/room.${props.productId}`, {}, JSON.stringify(msg))
        )
    }

    function bidMessage() {
        const bidMsg = {
            "chatType": "BID",
            "seller": `${props.sellerId}`,
            "buyer": `${props.id}`,
            "sender": `${props.id}`,
            "roomKey": `${props.productId}`,
            "message": talk
        }
        return(
            stomp_client.send(`/send/room.${props.productId}`, {}, JSON.stringify(bidMsg))
        )
    }
    
    function addChatLog(msg) {
        let talks = JSON.parse(msg.body)
        console.log(talks)
        // if (talks.sender === "seller") {
        //     chat.style.color = "red";
        // }
        const new_chatlog = chat_log.concat(talks.message)
        setChat_log(new_chatlog)
        const new_logs = logs.concat(talks)
        setLogs(new_logs)
    }

    console.log("메시지 출력")
    console.log(chat_log)
    console.log(logs)

    return (
        <div className='chatContainer'>

            <div className='chatBox'>
                <div className='chatContent'>
                    {logs[logs.length - 1]?.message}
                    {
                        logs.map((log) => {
                            return `${log.chatType}` === "CHAT" ?
                            <ul>{log.message}</ul> :
                            null
                        })
                    }
                </div>
                <input type="text" ref={inputBox} className="chatInput" placeholder="채팅을 입력해주세요!!" onChange={(e) => setTalk(e.target.value)} onKeyPress={(e) => { if (e.key === 'Enter') {sendMessage(); e.target.value=''}}} />
                    <br />
                <div className='btnSet'>
                    <button ref={msg_send_btn} onClick={sendMessage}>전송</button>
                    <button ref={msg_send_btn} onClick={bidMessage}>경매</button>
                </div>
                    <br />
            </div>
        </div>
    )
}

export default LiveChat