package edu.ssafy.lastmarket.service;


import edu.ssafy.lastmarket.TestUtils;
import edu.ssafy.lastmarket.domain.eneity.Category;
import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.domain.eneity.MemberCategory;
import edu.ssafy.lastmarket.repository.MemberCategoryRepository;
import edu.ssafy.lastmarket.repository.MemberRepository;
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
    MemberRepository memberRepository;

    MemberCategoryServiceImpl memberCategoryService;

    @Test
    public void addMemberCategory(){

        Optional<Member> memberOptional= TestUtils.getMemberOptional(TestUtils.getMember());
        lenient().doReturn(memberOptional).when(memberRepository).findByUsername(any());

        List<Category> categories = TestUtils.getCategories();
        List<MemberCategory> memberCategories = TestUtils.getMemberCategories(memberOptional.get(), categories);
        lenient().doReturn(memberCategories).when(memberCategoryRepository).saveAll(any());

        memberCategoryService = new MemberCategoryServiceImpl(memberCategoryRepository, memberRepository);
        List<MemberCategory> result = memberCategoryService.save(categories, "nick");


        assertThat(0L).isEqualTo(result.get(0).getId());
        assertThat(memberOptional.get()).isEqualTo(result.get(0).getMember());
        assertThat(categories.get(0)).isEqualTo(result.get(0).getCategory());






    }
}
