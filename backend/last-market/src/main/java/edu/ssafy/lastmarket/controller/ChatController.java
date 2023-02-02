package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;
import edu.ssafy.lastmarket.domain.dto.ChatType;
import edu.ssafy.lastmarket.service.TradeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RabbitTemplate template;
    private final TradeChatService tradeChatService;

    @MessageMapping("room.{roomKey}")
    public void chatSend(ChatMessageDTO msg, @DestinationVariable String roomKey) {
        log.info("[msg from {}]{}", roomKey, msg);
        if (msg.getChatType() == ChatType.TRADE_CHAT) {
            tradeChatService.makeChatRoom(msg);
            tradeChatService.saveChatLog(msg);
        }
        template.convertAndSend("chat.exchange", "room." + roomKey, msg);
    }

    @GetMapping("/api/chatLog/{chatRoomId}")
    public ResponseEntity<?> getChatLog(@PathVariable String chatRoomId) {
        log.info("chatlog find");
        TradeChat chatLog = tradeChatService.findChatLog(chatRoomId);
        return new ResponseEntity<>(chatLog, HttpStatus.OK);
    }
}
