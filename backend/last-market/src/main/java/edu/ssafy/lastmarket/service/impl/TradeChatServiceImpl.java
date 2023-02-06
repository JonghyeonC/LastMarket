package edu.ssafy.lastmarket.service.impl;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;
import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;
import edu.ssafy.lastmarket.exception.NoChatRoomException;
import edu.ssafy.lastmarket.repository.TradeChatRepository;
import edu.ssafy.lastmarket.service.TradeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeChatServiceImpl implements TradeChatService {
    private final TradeChatRepository tradeChatRepository;

    @Override
    @Transactional
    public void makeChatRoom(ChatMessageDTO msg) {
        String chatRommId = msg.getSeller() + "-" + msg.getBuyer() + "-" + msg.getRoomKey();
        Optional<TradeChat> chatRoom = tradeChatRepository.findById(chatRommId);
        if (chatRoom.isEmpty()) {
            log.info("[NO CHAT ROOM] save chat room id={}", chatRommId);
            TradeChat newChat = new TradeChat(chatRommId);
            tradeChatRepository.save(newChat);
        }
    }

    @Override
    public TradeChat findChatLog(String chatId) {
        return tradeChatRepository.findById(chatId)
                .orElseThrow(() -> new NoChatRoomException("없는 채팅방 입니다."));
    }

    @Override
    public void saveChatLog(ChatMessageDTO msg) {
        String chatId = msg.getSeller() + "-" + msg.getBuyer() + "-" + msg.getRoomKey();
        TradeChatDTO tradeChatDTO = TradeChatDTO.builder()
                .sender(msg.getSender())
                .msg(msg.getMessage())
                .sendDate(LocalDateTime.now())
                .build();
        tradeChatRepository.findAndPushFindById(chatId, tradeChatDTO);
    }
}
