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
    const [ bids, setBids ] = useState([''])

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
            "message": String(talk)
        }
        return(
            stomp_client.send(`/send/room.${props.productId}`, {}, JSON.stringify(bidMsg))
        )
    }

    function endBid() {
        const endMsg = {
            "chatType": "FINISH_BROADCAST",
            "seller": `${props.sellerId}`,
            "buyer": `${props.id}`,
            "sender": `${props.id}`,
            "roomKey": `${props.productId}`,
            "message": String(bids[bids.length - 1]?.message)
        }
        return(
            stomp_client.send(`/send/room.${props.productId}`, {}, JSON.stringify(endMsg))
        )
    }
    
    function addChatLog(msg) {
        let talks = JSON.parse(msg.body)
        console.log(talks)
        // if (talks.sender === "seller") {
        //     chat.style.color = "red";
        // }
        // const new_chatlog = chat_log.concat(talks.message)
        setChat_log([...chat_log, talks.message])
        // const new_logs = logs.concat(talks)
        setLogs([...logs, talks])
        setBids([...bids, talks])

    }

    console.log("메시지 출력")
    console.log(chat_log)
    console.log(logs)

    return (
        <div className='chatContainer'>
            <div className='chatBox'>
                <div className='chatContent'>
                    {
                        `${bids[bids.length - 1]?.chatType}` === "BID" ?
                        <div>{bids[bids.length - 1]?.message}</div> :
                        null
                    }
                    {
                        logs.map((log) => {
                            return `${log.chatType}` === "CHAT" ?
                            (
                                `${log.sender}` !== `${props.id}` ?
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
                    <button ref={msg_send_btn} onClick={bidMessage}>경매</button>
                    {
                        `${props.sellerId}` === `${props.id}` ?
                        <button ref={msg_send_btn} onClick={endBid}>낙찰</button> :
                        null
                    }
                </div>
                    <br />
            </div>
        </div>
    )
}

export default LiveChat