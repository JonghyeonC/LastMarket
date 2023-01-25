package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.eneity.Ban;
import edu.ssafy.lastmarket.domain.eneity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {
    Ban save(Ban ban);
    List<Ban> findByFrom(Member from);
    Optional<Ban> findByFromAndTo(Member from, Member to);
}
