package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.ChatListDTO;
import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;
import edu.ssafy.lastmarket.domain.entity.Member;

import java.util.List;

public interface TradeChatService {
    void makeChatRoom(ChatMessageDTO msg);
    TradeChat findChatLog(String chatId);
    void saveChatLog(ChatMessageDTO msg);
    List<ChatListDTO> sendChatList(Member member);
}
