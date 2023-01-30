package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query("select m from Member m left join fetch m.location left join fetch m.profile where m.username = :username")
    Optional<Member> findMemberFetchJoinByUsername(@Param("username")String username);
    Optional<Member> findByUsername(String username);
}
