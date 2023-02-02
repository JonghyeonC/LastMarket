package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.document.TradeChat;
import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeChatRepository extends MongoRepository<TradeChat, String> {
    Optional<TradeChat> findById(String id);

    TradeChat save(TradeChat tradeChat);

    @Update("{ _id: ?0 , $push: { chatLogs : ?1 }}")
    void findAndPushFindById(String id, TradeChatDTO tradeChatDTO);
}
