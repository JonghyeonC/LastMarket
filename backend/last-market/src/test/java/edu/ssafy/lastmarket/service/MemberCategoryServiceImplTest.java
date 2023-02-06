package edu.ssafy.lastmarket.service;


import edu.ssafy.lastmarket.TestUtils;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.MemberCategory;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import edu.ssafy.lastmarket.repository.MemberCategoryRepository;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.service.impl.MemberCategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class MemberCategoryServiceImplTest {

    @Mock
    MemberCategoryRepository memberCategoryRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    MemberRepository memberRepository;

    MemberCategoryServiceImpl memberCategoryService;

    @Test
    public void addMemberCategory(){

        Optional<Member> memberOptional= TestUtils.getMemberOptional(TestUtils.getMember());
        lenient().doReturn(memberOptional).when(memberRepository).findByUsername(any());

        List<CategoryName> categoryNames = TestUtils.getCategoryNames();
        List<MemberCategory> memberCategories = TestUtils.getMemberCategories(memberOptional.get(), categoryNames);
        lenient().doReturn(memberCategories).when(memberCategoryRepository).saveAll(any());

        memberCategoryService = new MemberCategoryServiceImpl(memberCategoryRepository, categoryRepository);
        List<MemberCategory> result = memberCategoryService.save(categoryNames, TestUtils.getMember());


        assertThat(0L).isEqualTo(result.get(0).getId());
        assertThat(memberOptional.get()).isEqualTo(result.get(0).getMember());
        CategoryName categoryName = categoryNames.get(0);
        assertThat(categoryName).isEqualTo(result.get(0).getCategory().getCategoryName());






    }
}
