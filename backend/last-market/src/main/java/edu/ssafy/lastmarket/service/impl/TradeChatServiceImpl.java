package edu.ssafy.lastmarket.service.impl;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.ChatListDTO;
import edu.ssafy.lastmarket.domain.dto.ChatMessageDTO;
import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.exception.NoChatRoomException;
import edu.ssafy.lastmarket.exception.NotAuthenticated;
import edu.ssafy.lastmarket.exception.NotFoundException;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.repository.TradeChatRepository;
import edu.ssafy.lastmarket.service.TradeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeChatServiceImpl implements TradeChatService {
    private final TradeChatRepository tradeChatRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void makeChatRoom(ChatMessageDTO msg) {
        String chatRoomId = msg.getSeller() + "-" + msg.getBuyer() + "-" + msg.getRoomKey();
        Optional<TradeChat> chatRoom = tradeChatRepository.findById(chatRoomId);
        if (chatRoom.isEmpty()) {
            log.info("[NO CHAT ROOM] save chat room id={}", chatRoomId);
            TradeChat newChat = new TradeChat(chatRoomId);
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

    @Override
    public List<ChatListDTO> sendChatList(Member member) {
        if (member == null) {
            throw new NotAuthenticated("인증이 필요합니다.");
        }
        Long loginMemberId = member.getId();

        String findSeller = member.getId() + "-";
        String findBuyer = "-" + member.getId() + "-";
        List<TradeChat> byJoinSeller = tradeChatRepository.findByJoinSeller(findSeller);
        List<TradeChat> byJoinBuyer = tradeChatRepository.findByJoinBuyer(findBuyer);

        List<ChatListDTO> result = byJoinBuyer.stream()
                .map(tradeChat -> ChatListDTOMapper(loginMemberId, tradeChat))
                .collect(Collectors.toList());
        byJoinSeller.stream()
                .map(tradeChat -> ChatListDTOMapper(loginMemberId, tradeChat))
                .forEach(result::add);

        return result;
    }

    private ChatListDTO ChatListDTOMapper(Long loginMemberId, TradeChat tradeChat) {
        String[] info = tradeChat.getId().split("-");
        Long sellerId = Long.parseLong(info[0]);
        Long buyerId = Long.parseLong(info[1]);
        Long productId = Long.parseLong(info[2]);

        ChatListDTO chatListDTO = new ChatListDTO();

        compareIdAndMapping(loginMemberId, sellerId, chatListDTO);
        compareIdAndMapping(loginMemberId, buyerId, chatListDTO);
        chatListDTO.setProductId(productId);
        int index = tradeChat.getChatLogs().size() - 1;
        chatListDTO.setLastChat(tradeChat.getChatLogs().get(index));
        chatListDTO.setRoomId(tradeChat.getId());

        return chatListDTO;
    }

    private void compareIdAndMapping(Long loginMemberId, Long compareId, ChatListDTO chatListDTO) {
        if (!compareId.equals(loginMemberId)) {
            chatListDTO.setOtherId(compareId);
            Member member = memberRepository.findById(compareId)
                    .orElseThrow(() -> new NotFoundException("없는 유저입니다."));
            chatListDTO.setOtherName(member.getNickname());
            if (member.getProfile() != null) {
                chatListDTO.setOtherImageUrl(member.getProfile().getImageURL());
            } else {
                chatListDTO.setOtherImageUrl("");
            }
        }
    }
}
