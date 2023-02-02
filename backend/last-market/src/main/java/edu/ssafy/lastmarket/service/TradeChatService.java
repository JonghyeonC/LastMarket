package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;

public interface TradeChatService {
    void makeChatRoom(ChatMessageDTO msg);
    TradeChat findChatLog(String chatId);
    void saveChatLog(ChatMessageDTO msg);
}
