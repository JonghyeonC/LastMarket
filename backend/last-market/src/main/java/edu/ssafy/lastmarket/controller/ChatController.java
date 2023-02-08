package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;
import edu.ssafy.lastmarket.domain.dto.ChatType;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.exception.NotFoundException;
import edu.ssafy.lastmarket.exception.NotMatchSellerException;
import edu.ssafy.lastmarket.service.MemberService;
import edu.ssafy.lastmarket.service.ProductService;
import edu.ssafy.lastmarket.service.TradeChatService;
import edu.ssafy.lastmarket.service.TradeService;
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
    private final TradeService tradeService;
    private final ProductService productService;
    private final MemberService memberService;

    @MessageMapping("room.{roomKey}")
    public void chatSend(ChatMessageDTO msg, @DestinationVariable String roomKey) {
        log.info("[msg from {}]{}", roomKey, msg);
        if (msg.getChatType() == ChatType.TRADE_CHAT) {
            tradeChatService.makeChatRoom(msg);
            tradeChatService.saveChatLog(msg);
        } else if (msg.getChatType() == ChatType.FINISH) {
            Product product = productService.findProductMemberById(Long.parseLong(msg.getRoomKey()))
                    .orElseThrow(() -> new NotFoundException("없는 제품입니다."));
            if (msg.getSender().equals(msg.getSeller())) {
                Member buyer = memberService.findMemberById(Long.parseLong(msg.getBuyer()));
                tradeService.saveTrade(product, buyer);
                productService.successBid(product,msg.getMessage());
            } else {
                throw new NotMatchSellerException("판매자가 아닙니다.");
            }
        }
        template.convertAndSend("chat.exchange", "room." + roomKey, msg);
    }

    @GetMapping("/api/chatLog/{chatRoomId}")
    public ResponseEntity<?> getChatLog(@PathVariable String chatRoomId) {
        log.info("chatlog find");
        TradeChat chatLog = tradeChatService.findChatLog(chatRoomId);
        log.info("[chat logs]{}", chatLog);
        return new ResponseEntity<>(chatLog, HttpStatus.OK);
    }
}
