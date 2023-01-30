package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RabbitTemplate template;

    @MessageMapping("room.{roomKey}")
    public void chatSend(ChatMessageDTO msg, @DestinationVariable String roomKey) {
        log.info("[msg from {}]{}", roomKey, msg);
        template.convertAndSend("chat.exchange", "room." + roomKey, msg);
    }
}
