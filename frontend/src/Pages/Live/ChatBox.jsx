import React, { useRef, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from "react-redux";
import Message from './Message';
import AttachFileOutlinedIcon from '@mui/icons-material/AttachFileOutlined';

import SockJS from "sockjs-client";
import Stomp from "stompjs";
import styled from 'styled-components';
import { getChatMessage, subMessage } from '../../redux/slices/chatSlice';
import axios from 'axios';

const Container = styled.div`
    flex: 6;
`;

const ChatBoxWrapper = styled.div`
    padding:10px 20px;
    height:100%;
    display: flex;
    flex-direction: column;
    `;

const ChatBoxTop = styled.div`
    height:100%;
    overflow-y: scroll;
    padding-right: 10px;
    `;

const ChatBoxBottom = styled.div`
    margin-top: 5px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    position:relative;
    `;

const ChatMessageInput = styled.textarea`
    width: 80%;
    height: 50px;
    padding: 10px;
    margin-bottom: 5%;
    `;

const ChatSubmitButton = styled.button`
    width: 70px;
    height: 40px;
    border:none;
    border-radius:5px;
    cursor:pointer;
    background-color: #FAE57C;
    `;

const FileUpload = styled.div`
    cursor:pointer;
    position:absolute;
    bottom:0;
    left:1%;    
    color: gray;
`;


const ChatBox = () => {
    const scrollRef = useRef();
    const ws = useRef();
    const params = useParams();
    const roomId = params.id;
    const dispatch = useDispatch();
    const [otherUserInfo, setOtherUserInfo] = useState({});
    const [text, setText] = useState("");

    const messages = useSelector((state) => state.chatReducer.messages.result);

    const config = {
        headers: {
            "X-ACCESS-TOKEN": window.localStorage.getItem("token")
        }
    }


    // 소켓 연결
    useEffect(() => {
        let sock = new SockJS(`https://i8d206.p.ssafy.io`);
        let client = Stomp.over(sock);
        ws.current = client;

        ws.current.connect({}, () => {
            ws.current.subscribe(`/sub/chat/room/${roomId}`, (res) => {
                const message = JSON.parse(res.body);
                dispatch(subMessage(message));
            });
        })
    }, [roomId]);

    useEffect(() => {
        dispatch(getChatMessage({roomId}));
    }, []);

    useEffect(() => {
        axios.get(`/api/chat-rooms/otherUserInfo/${roomId}`, config)
            .then((response) => {
                setOtherUserInfo(response.data.result.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    useEffect(() => {
        scrollRef.current.scrollIntoView({ block: "end" });
    }, [messages]);

    // 메시지 발신
    const send = async () => {
        try {
            // send할 데이터
            const message = {
                roomId: roomId,
                message: text,
                otherUserIds: [5], // 메시지 받는 상대방
                type:"TALK",
                userId:1 // 임시
            };

            if (text === "") {
                return;
            }
            // send message
            ws.current.send("/pub/chat/message", {}, JSON.stringify(message));
            setText("");
        } catch (error) {
            console.log(error);
        }
    };

    //setTimeout(() => ws.current.send("/pub/chat/message", {}, JSON.stringify({"roomId":1, "otherUserIds":[5], "userId":1, "type":0, "message":"아녕하세요!!ㄸ"})), 500); 

    if (messages === null) {
        return;
    }

    return (
    <Container>
        <ChatBoxWrapper>
            <ChatBoxTop>
                <div ref={scrollRef}>
                    {messages !== undefined && messages.data.map((message, idx) => (
                        <Message createdAt={message.createdAt} key={idx} message={message.message} me={message.userId === 1}/>
                    ))}
                </div>
            </ChatBoxTop>

            <ChatBoxBottom>
                <ChatMessageInput value={text} onChange={(e) => setText(e.target.value)}></ChatMessageInput>
                <ChatSubmitButton onClick={() => send()}>전송</ChatSubmitButton>
                <label for="file">
                    <FileUpload><AttachFileOutlinedIcon style={{"fontSize":"22px"}} /></FileUpload>
                </label>
                <input style={{"display":"none"}} type="file" name="file" id="file" />
            </ChatBoxBottom>
        </ChatBoxWrapper>
    </Container>
    )
}

export default ChatBox