package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;

public interface TradeChatService {
    TradeChat findChatLog(String chatId);

    void saveChatLog(String chatId, TradeChatDTO tradeChatDTO);
}
