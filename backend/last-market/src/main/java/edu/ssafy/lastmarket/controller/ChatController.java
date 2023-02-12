package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.ChatListDTO;
import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;
import edu.ssafy.lastmarket.domain.dto.ChatType;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.Trade;
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

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RabbitTemplate template;
    private final TradeChatService tradeChatService;
    private final TradeService tradeService;
    private final ProductService productService;
    private final MemberService memberService;

    //TODO : service로 분리하기
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
                Trade trade = tradeService.saveTrade(product, buyer);
                productService.successBid(product, msg.getMessage());
                // TODO : tradeId 보내주기
                template.convertAndSend("chat.exchange", "room." + roomKey, "{\"msg\" : \"" + trade.getId() + "\"}");
            } else {
                throw new NotMatchSellerException("판매자가 아닙니다.");
            }
        }
        template.convertAndSend("chat.exchange", "room." + roomKey, msg);
    }

    @GetMapping("/api/chatLog/{chatRoomId}")
    public ResponseEntity<?> getChatLog(@PathVariable String chatRoomId) {
        TradeChat chatLog = tradeChatService.findChatLog(chatRoomId);
        return new ResponseEntity<>(chatLog, HttpStatus.OK);
    }

    @GetMapping("/api/chats")
    public ResponseEntity<?> getChatList(@Login Member member) {
        List<ChatListDTO> chatList = tradeChatService.sendChatList(member);
        return new ResponseEntity<>(chatList, HttpStatus.OK);
    }
}
