package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.MemberCategory;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import edu.ssafy.lastmarket.repository.MemberCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCategoryServiceImpl implements MemberCategoryService {

    private final MemberCategoryRepository memberCategoryRepository;
    private final CategoryRepository categoryRepository;
    @Override
    @Transactional
    public List<MemberCategory> save(List<CategoryName> categoryNames, Member member) {


        List<MemberCategory> list =new ArrayList<>();
        for (CategoryName categoryName : categoryNames) {
            Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);

            if(categoryOptional.isEmpty()){
                Category category = new Category(categoryName);
                categoryRepository.save(category);
                categoryOptional = Optional.of(category);
            }

            list.add(new MemberCategory(member,categoryOptional.get()));
        }

        List<MemberCategory> result = memberCategoryRepository.saveAll(list);

        return result;
    }
}
