package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByMemberAndProductId(Member member, Long ProductId);
    Boolean existsByMemberAndProductId(Member member, Long ProductId);

}
