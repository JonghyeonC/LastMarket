package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
    ArrayList<MemberCategory> findByMember(Member member);
}
