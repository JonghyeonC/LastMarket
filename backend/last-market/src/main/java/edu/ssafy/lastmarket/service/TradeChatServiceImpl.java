package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;
import edu.ssafy.lastmarket.exception.NoChatRoomException;
import edu.ssafy.lastmarket.repository.TradeChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeChatServiceImpl implements TradeChatService {
    private final TradeChatRepository tradeChatRepository;

    @Override
    public TradeChat findChatLog(String chatId) {
        return tradeChatRepository.findById(chatId)
                .orElseThrow(() -> new NoChatRoomException("없는 채팅방 입니다."));
    }

    @Override
    public void saveChatLog(String chatId, TradeChatDTO tradeChatDTO) {
        tradeChatRepository.findAndPushFindById(chatId, tradeChatDTO);
    }
}
