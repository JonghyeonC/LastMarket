package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.MemberCategory;
import edu.ssafy.lastmarket.repository.MemberCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCategoryServiceImpl implements MemberCategoryService {

    private final MemberCategoryRepository memberCategoryRepository;

    @Override
    public List<MemberCategory> save(List<Category> categories, Member member) {


        List<MemberCategory> list =new ArrayList<>();
        for (Category category : categories) {
            list.add(new MemberCategory(member,category));
        }

        List<MemberCategory> result = memberCategoryRepository.saveAll(list);

        return result;
    }
}
