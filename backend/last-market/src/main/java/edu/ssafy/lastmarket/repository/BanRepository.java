package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.eneity.Ban;
import edu.ssafy.lastmarket.domain.eneity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanRepository extends JpaRepository<Long, Ban> {
    Ban save(Ban ban);
    List<Ban> findByFrom(Member from);
    boolean exists(Ban ban);
}
