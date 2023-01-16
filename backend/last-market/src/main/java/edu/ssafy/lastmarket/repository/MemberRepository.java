package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.eneity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByUsername(String username);
}
