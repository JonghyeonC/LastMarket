package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Category;
import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.domain.eneity.MemberCategory;
import edu.ssafy.lastmarket.repository.MemberCategoryRepository;
import edu.ssafy.lastmarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCategoryServiceImpl implements MemberCategoryService {

    private final MemberCategoryRepository memberCategoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<MemberCategory> save(List<Category> categories, String username) {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);


        List<MemberCategory> list =new ArrayList<>();
        for (Category category : categories) {
            list.add(new MemberCategory(memberOptional.get(),category));
        }

        list = memberCategoryRepository.saveAll(list);

        return list;
    }
}
