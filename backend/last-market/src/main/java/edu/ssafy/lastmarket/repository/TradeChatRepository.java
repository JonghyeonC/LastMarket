package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface TradeChatRepository extends MongoRepository<TradeChat, String> {
    Optional<TradeChat> findById(String id);

    @Update("{ '$push' : { ?1 : { 'chatLogs' : ?2 } } }")
    void findAndPushFindById(String id, TradeChatDTO tradeChatDTO);
}
