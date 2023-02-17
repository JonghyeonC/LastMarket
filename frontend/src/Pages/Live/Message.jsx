import React from 'react'
import styled from 'styled-components'
import {format} from 'timeago.js';


const Container = styled.div`
    display: flex;
    flex-direction: column;
    margin-top: 20px;
    ${(props) => props.me === true && `
        align-items: flex-end;
    `}
`;

const MessageTop = styled.div`
    display: flex;
`;

const MessageBottom = styled.div`
    font-size: 12px;
    margin-top: 10px; 
`;

const MessageImage = styled.img`
    width: 32px;
    height: 32px;
    border-radius: 50%;
    object-fit:cover;
    margin-right: 10px;

    ${(props) => props.me === true && `
        display:none;
    `}
`;

const MessageText = styled.p`
    padding: 10px;
    border-radius: 20px;
    ${(props) => props.me === false && `
      border-top-left-radius: 0%;
    `}
    ${(props) => props.me === true && `
      border-top-right-radius:0%;
    `}
    background-color: #f3f3f3;
    max-width: 300px;
    color: #202020;
    font-size: 14px;

    ${(props) => props.me === true && `
        background-color:#FAE57C;
    `}
`;

const timeForToday = (value) => {
  const today = new Date();
  const timeValue = new Date(value);

  const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
  if (betweenTime < 1) return '방금전';
  if (betweenTime < 60) {
      return `${betweenTime}분전`;
  }

  const betweenTimeHour = Math.floor(betweenTime / 60);
  if (betweenTimeHour < 24) {
      return `${betweenTimeHour}시간전`;
  }

  const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
  if (betweenTimeDay < 365) {
      return `${betweenTimeDay}일전`;
  }

  return `${Math.floor(betweenTimeDay / 365)}년전`;
}

const Message = ({me, message, createdAt}) => {
  return (
    <Container me={me}>
      <MessageTop>
        <MessageImage me={me} src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZ9CpA4I6pOTcI6CNL9vo0T_hUU_xreMcfA2ZTUmH4NuQ0TmwlqquuJdE88LbOBR3zQyE&usqp=CAU" alt=""></MessageImage>
        <MessageText me={me}>{message}</MessageText>
      </MessageTop>

      <MessageBottom>
      {timeForToday(createdAt)}
      </MessageBottom>
    </Container>
  )
}

export default Message
