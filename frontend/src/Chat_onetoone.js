import { useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { useLocation } from "react-router-dom";
import './Chat.css'

function Chat_onetoone(props) {

    const msg_send_btn = useRef()
    const inputBox = useRef()

    const location = useLocation()
    const productId = location.state.productId
    const id = location.state.id
    const sellerId = location.state.sellerId

    const [talk, setTalk] = useState('')
    const [chat_log, setChat_log] = useState([''])
    const [ logs, setLogs ] = useState([''])

    const socket = new SockJS("https://i8d206.p.ssafy.io/api/ws");
    console.log(socket)
    const stomp_client = Stomp.over(socket);
    
    stomp_client.connect({}, () => {
        console.log("connection successe")
        stomp_client.subscribe(`/exchange/chat.exchange/room.${productId}`, (message) => {
            addChatLog(message)
        });
    })


    function sendMessage() {
        const msg = {
            "chatType": "TRADE_CHAT",
            "seller": sellerId,
            "buyer": id,
            "sender": id,
            "roomKey": productId,
            "message": talk
        }
        return(
            stomp_client.send(`/send/room.${productId}`, {}, JSON.stringify(msg))
        )
    }

    function doneDeal() {
        const msg = {
            "chatType": "FINISH_TRADE",
            "seller": sellerId,
            "buyer": id,
            "sender": id,
            "roomKey": productId,
            "message": ""
        }
        return(
            stomp_client.send(`/send/room.${productId}`, {}, JSON.stringify(msg))
        )
    }
    
    function addChatLog(msg) {
        let talks = JSON.parse(msg.body)
        console.log(talks)
        setChat_log([...chat_log, talks.message])
        setLogs([...logs, talks])
    }

    console.log("메시지 출력")
    console.log(chat_log)
    console.log(logs)

    return (
        <div className='chatContainer'>

            <div className='chatBox'>
                <div className='chatContent'>
                    {
                        logs.map((log) => {
                            return `${log.chatType}` === "TRADE_CHAT" ?
                            (
                                `${log.sender}` !== `${id}` ?
                                <div className='yourMsg'>{log.message}</div> :
                                <div className='myMsg'>{log.message}</div>
                            ) :
                            null
                        })
                    }
                </div>
                <input type="text" ref={inputBox} className="chatInput" placeholder="채팅을 입력해주세요!!" onChange={(e) => setTalk(e.target.value)} onKeyPress={(e) => { if (e.key === 'Enter') {sendMessage(); e.target.value=''}}} />
                    <br />
                <div className='btnSet'>
                    <button ref={msg_send_btn} onClick={sendMessage}>전송</button>
                    {
                        `${sellerId}` === `${id}` ?
                        <button onClick={() => {doneDeal() ; navigate('/detail/' + (productId))}}>거래 종료</button> :
                        null
                    }
                </div>
                    <br />
            </div>
        </div>
    )
}

export default Chat_onetoone