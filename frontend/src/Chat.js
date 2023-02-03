function LiveChat() {

    // let msg_send_btn = document.getElementById("msg");
    // let chat_log = document.getElementById("chat-log");
    // let userId = "none";
    
    // let socket = new WebSocket("wss://i8d206.p.ssafy.io");
    // console.log(socket)
    // let stomp_client = Stomp.over(socket);
    
    // stomp_client.debug = null;
    
    // stomp_client.connect({}, () => {
    //     stomp_client.subscribe("/exchange/chat.exchange/room.chat-test", (message) => {
    //         addChatLog(JSON.parse(message.body))
    //     });
    // })
    
    // msg_send_btn.addEventListener("click", () => {
    //     userId = document.getElementById("id").value;
    //     let test_message = {
    //         "type": "test",
    //         "nickname": userId,
    //         "msg": "test from js"
    //     }
    //     stomp_client.send("/send/room.chat-test", {}, JSON.stringify(test_message));
    // })
    
    // function addChatLog(msg) {
    //     let chat = document.createElement("div");
    //     chat.innerText = msg.msg;
    //     if (msg.nickname === userId) {
    //         chat.style.color = "red";
    //     }
    //     chat_log.append(chat);
    // }


    return (
        <div>
            <input type="text" id="id" placeholder="채팅을 입력해주세요!!" />
                <br />
            <button id="msg">msg send</button>
                <br />
            <div id="chat-log"></div>
        </div>
    )
}

export default LiveChat